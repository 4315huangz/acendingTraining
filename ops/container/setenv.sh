export JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=AKIAZUQLTKZRIHWEALLF"
export JAVA_OPTS="$JAVA_OPTS -Daws.secretKey=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"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.driver=org.postgresql.Driver"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export JAVA_OPTS="$JAVA_OPTS -Dsecret.key=12345678"
export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.org.springframework=INFO"
export JAVA_OPTS="$JAVA_OPTS -Dlogging.levelt.com.ascending=TRACE"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.databaseName=training_db"
#export JAVA_OPTS="$JAVA_OPTS -Ddatabase.portNumber=5431"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.user=admin"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.password=Training123!"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.url=jdbc:postgresql://172.17.0.2:5432/training_db"
export JAVA_OPTS="$JAVA_OPTS -Dserver.port=8080"
export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=div"