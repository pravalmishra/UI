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
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }

        stage('Publish Extent Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false, 
                    keepAll: true, 
                    reportDir: 'reports', 
                    reportFiles: 'TestExecutionReport.html', 
                    reportName: 'HTML Regression Extent Report', 
                    reportTitles: ''
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/allure-results/*', allowEmptyArchive: true
        }

        success {
            emailext subject: "Jenkins Job SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     body: """<p><b>Build SUCCESSFUL</b></p>
                              <p><b>Project:</b> ${env.JOB_NAME}</p>
                              <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                              <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                     recipientProviders: [[$class: 'DevelopersRecipientProvider']],
                     to: 'pravalm@gmail.com',
                     mimeType: 'text/html'
        }

        unstable {
            emailext subject: "Jenkins Job UNSTABLE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     body: """<p><b>Build UNSTABLE</b> (likely test failures)</p>
                              <p><b>Project:</b> ${env.JOB_NAME}</p>
                              <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                              <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                     to: 'pravalm@gmail.com',
                     mimeType: 'text/html'
        }

        failure {
            emailext subject: "Jenkins Job FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                     body: """<p><b>Build FAILED</b></p>
                              <p><b>Project:</b> ${env.JOB_NAME}</p>
                              <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                              <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>""",
                     to: 'pravalm@gmail.com',
                     mimeType: 'text/html'
        }
    }
}
