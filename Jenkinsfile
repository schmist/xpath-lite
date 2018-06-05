#!usr/bin/env/groovy

pipeline {
    agent {
        node {
            label 'docker'
        }
    }

    parameters {
    	password(description: 'Publish Password', name: 'publishPassword')
    }

    stages {
		stage('Build') {
			steps {
				sh 'buildstack update && buildstack --version'
				sh 'buildstack run "./gradlew build" "openjdk8"'
			}
		}

		stage('Publish') {
			steps {
				sh "buildstack run -e PUBILISH_PASSWORD=${params.publishPassword} --buildnumber ${BUILD_NUMBER} './gradlew -PtechVersion=\${BUILD_NUMBER} -PpublishPassword=\${PUBILISH_PASSWORD} publish' openjdk8"
			}
		}
    }
}