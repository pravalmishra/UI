pipeline {
    agent any

    tools {
        maven 'maven'              // Defined in Jenkins > Global Tool Config
        jdk 'JDK 21'               // Same here
        allure 'allure'            // Same here
    }

    environment {
        JAVA_HOME = "${tool 'JDK 21'}"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/pravalmishra/UI'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
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
