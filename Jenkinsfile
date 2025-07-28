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

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/allure-results/*', allowEmptyArchive: true
        }
    }
}
