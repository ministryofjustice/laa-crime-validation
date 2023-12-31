{{/* vim: set filetype=mustache: */}}
{{/*
Environment variables for service containers
*/}}
{{- define "laa-crime-validation.env-vars" }}
env:
  - name: AWS_REGION
    value: {{ .Values.aws_region }}
  - name: SENTRY_DSN
    value: {{ .Values.sentry.dsn }}
  - name: SENTRY_ENV
    value: {{ .Values.java.host_env }}
  - name: SENTRY_SAMPLE_RATE
    value: {{ .Values.sentry.sampleRate | quote }}
  - name: MAAT_API_BASE_URL
    value: {{ .Values.maatApi.baseUrl }}
  - name: MAAT_API_OAUTH_URL
    value: {{ .Values.maatApi.oauthUrl }}
  - name: MAAT_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: maat-api-oauth-client-id
        key: MAAT_API_OAUTH_CLIENT_ID
  - name: MAAT_API_OAUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: maat-api-oauth-client-secret
        key: MAAT_API_OAUTH_CLIENT_SECRET
  - name: CMA_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: cma-api-oauth-client-id
        key: CMA_API_OAUTH_CLIENT_ID
  - name: CMA_API_OAUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: cma-api-oauth-client-secret
        key: CMA_API_OAUTH_CLIENT_SECRET
  - name: CCP_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: ccp-api-oauth-client-id
        key: CCP_API_OAUTH_CLIENT_ID
  - name: CCP_API_OAUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: ccp-api-oauth-client-secret
        key: CCP_API_OAUTH_CLIENT_SECRET
  - name: CCC_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: ccc-api-oauth-client-id
        key: CCC_API_OAUTH_CLIENT_ID
  - name: CCC_API_OAUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: ccc-api-oauth-client-secret
        key: CCC_API_OAUTH_CLIENT_SECRET
  - name: HARDSHIP_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: hardship-api-oauth-client-id
        key: HARDSHIP_API_OAUTH_CLIENT_ID
  - name: HARDSHIP_API_OAUTH_CLIENT_SECRET
    valueFrom:
      secretKeyRef:
        name: hardship-api-oauth-client-secret
        key: HARDSHIP_API_OAUTH_CLIENT_SECRET
  - name: ORCHESTRATION_API_OAUTH_CLIENT_ID
    valueFrom:
      secretKeyRef:
        name: orchestration-api-oauth-client-id
        key: ORCHESTRATION_API_OAUTH_CLIENT_ID
  - name: ORCHESTRATION_API_OAUTH_CLIENT_SECRET
    valueFrom:
        secretKeyRef:
            name: orchestration-api-oauth-client-secret
            key: ORCHESTRATION_API_OAUTH_CLIENT_SECRET
  - name: JWT_ISSUER_URI
    value: {{ .Values.jwt.issuerUri }}
  - name: DATASOURCE_HOST_PORT
    valueFrom:
      secretKeyRef:
        name: rds-postgresql-instance-output
        key: rds_instance_endpoint
  - name: DATASOURCE_DBNAME
    valueFrom:
      secretKeyRef:
        name: rds-postgresql-instance-output
        key: database_name
  - name: DATASOURCE_USERNAME
    valueFrom:
      secretKeyRef:
        name: rds-postgresql-instance-output
        key: database_username
  - name: DATASOURCE_PASSWORD
    valueFrom:
      secretKeyRef:
        name: rds-postgresql-instance-output
        key: database_password
{{- end -}}
