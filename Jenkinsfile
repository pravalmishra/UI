pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'JDK 21'
        allure 'allure'
    }

    environment {
        JAVA_HOME = "${tool 'JDK 21'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Automation Project') {
            steps {
                // Clone the correct project repo
                git branch: 'main', url: 'https://github.com/pravalmishra/UI'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml -Denv=dev'
            }
        }

         stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }

         stage('Publish Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: true, 
                                  reportDir: 'reports', 
                                  reportFiles: 'TestExecutionReport.html', 
                                  reportName: 'HTML Regression Extent Report', 
                                  reportTitles: ''])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/allure-results/*', allowEmptyArchive: true
        }
    }
}
