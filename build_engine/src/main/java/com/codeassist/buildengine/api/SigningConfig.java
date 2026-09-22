package com.codeassist.buildengine.api;
import java.nio.file.Path;
public final class SigningConfig {
  public final Path keystorePath; public final String alias, storePassword, keyPassword;
  public SigningConfig(Path keystorePath, String alias, String storePassword, String keyPassword) {
    this.keystorePath=keystorePath; this.alias=alias; this.storePassword=storePassword; this.keyPassword=keyPassword;
  }
}
