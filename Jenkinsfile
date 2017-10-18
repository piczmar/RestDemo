pipeline {
    agent any
    environment{
        CONN = 'marcin@172.17.0.1'
        TOMCAT_HOME = '/home/marcin/tools/apache-tomcat-8.5.23'
        ENV_URL = 'http://172.17.0.1:8080/RestDemo-0.0.1-SNAPSHOT'
        INITIALIZE_URL = 'http://172.17.0.1:8080/RestDemo-0.0.1-SNAPSHOT/demo'
    }
    tools {
        maven 'mvn_3.5'
        jdk 'JDK1.8'
    }
    stages{
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }

        }

        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true clean install'
            }
        }
        stage ('Deploy') {
            steps {
                sh 'ssh ${CONN} rm -f -R ${TOMCAT_HOME}/webapps/RestDemo*'
                sh 'ssh ${CONN} ls ${TOMCAT_HOME}/webapps/'
                sh 'scp app/target/RestDemo-0.0.1-SNAPSHOT.war ${CONN}:${TOMCAT_HOME}/webapps/'
            }
        }
        stage ('Start tomcat') {
            steps {
                sh 'ssh ${CONN} "${TOMCAT_HOME}/bin/catalina.sh start"'
                sh 'chmod +x wait_until_deployed.sh'
                sh './wait_until_deployed.sh ${INITIALIZE_URL}'
            }
        }
        stage ('Functional tests') {
            steps {
                sh 'mvn install -Pstaging'
            }
            post {
                success {
                    junit 'target/**/*.xml'
                    jacoco(execPattern: 'ft-staging/target/jacoco.exec')
                    archive "target/**/*"
                }
            }
        }
        stage ('Stop tomcat') {
            steps {
                sh 'ssh ${CONN} "${TOMCAT_HOME}/bin/catalina.sh stop"'
            }
        }
    }
}