pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 11'
    }

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select browser for UI tests'
        )
        string(
            name: 'THREAD_COUNT',
            defaultValue: '2',
            description: 'Number of parallel threads'
        )
        string(
            name: 'CUCUMBER_TAGS',
            defaultValue: '@ui',
            description: 'Cucumber tags to execute'
        )
    }

    options {
        timeout(time: 1, unit: 'HOURS')
        ansiColor('xterm')
        timestamps()
    }

    stages {
        stage('Validate Parameters') {
            steps {
                script {
                    if (!params.BROWSER || !params.THREAD_COUNT || !params.CUCUMBER_TAGS) {
                        error 'Required parameters are missing'
                    }
                }
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean Workspace') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Run UI Tests') {
            steps {
                script {
                    try {
                        sh """
                            mvn test \
                            -Dcucumber.filter.tags="${params.CUCUMBER_TAGS}" \
                            -Dbrowser=${params.BROWSER} \
                            -Ddataproviderthreadcount=${params.THREAD_COUNT}
                        """
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Test execution failed: ${e.getMessage()}"
                    }
                }
            }
        }

        stage('Generate Reports') {
            steps {
                script {
                    // Additional report generation if needed
                    sh 'mvn site'
                }
            }
        }
    }

    post {
        always {
            // Publish Cucumber HTML reports
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/reports',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber Test Report',
                reportTitles: "UI Test Results - ${params.BROWSER}"
            ])

            // Archive the test results and reports
            archiveArtifacts(
                artifacts: '''
                    target/reports/**/*,
                    target/surefire-reports/**/*
                ''',
                fingerprint: true
            )

            // Publish JUnit test results
            junit(
                allowEmptyResults: true,
                testResults: 'target/surefire-reports/*.xml'
            )

            // Send email notification
            emailext(
                subject: "Pipeline Status: ${currentBuild.result}",
                body: """
                    Job: ${env.JOB_NAME}
                    Build: ${env.BUILD_NUMBER}
                    Status: ${currentBuild.result}

                    Browser: ${params.BROWSER}
                    Thread Count: ${params.THREAD_COUNT}
                    Tags: ${params.CUCUMBER_TAGS}

                    Check console output at: ${env.BUILD_URL}
                    Test Report: ${env.BUILD_URL}Cucumber_Test_Report/
                """,
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )

            // Clean workspace
            cleanWs()
        }

        success {
            echo 'Test execution completed successfully!'
        }

        failure {
            echo 'Test execution failed!'
        }

        unstable {
            echo 'Test execution was marked as unstable'
        }
    }
}