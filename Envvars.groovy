// Configure global env vars
def envvars = { instance, env_var_map ->
  globalNodeProperties = instance.getGlobalNodeProperties()
  envVarsNodePropertyList = globalNodeProperties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)

  newEnvVarsNodeProperty = null
  envVars = null

  if ( !envVarsNodePropertyList ) {
    newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
  } else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
  }

  env_var_map.each { k, v ->
    envVars.put(k, v)
  }
  println "Setting environment variables"
}
