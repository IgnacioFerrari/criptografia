## Generar claves
```
keytool -genkeypair -alias emisor -keyalg RSA -keysize 2048 \
  -dname "CN=utn" -validity 365 -storetype PKCS12 \
  -keystore emisor_keystore.p12 -storepass utn_frba_criptografia_2021
```

## Generar certificado
```
keytool -exportcert -alias emisor -storetype PKCS12 \
  -keystore emisor_keystore.p12 -file \
  emisor_certificate.cer -rfc -storepass utn_frba_criptografia_2021
```

## Importar certificado
```
keytool -importcert -alias receptor -storetype PKCS12 \
  -keystore receptor_keystore.p12 -file \
  emisor_certificate.cer -rfc -storepass utn_frba_criptografia_2021
```