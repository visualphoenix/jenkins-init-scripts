// Configure global CAS security realm
def cas = {
    instance, casServerUrl,
    forceRenewal=false, enableSingleSignOut=true,
    casAuthoritiesAttribute="groups,roles", casFullNameAttribute='cn',
    casEmailAttribute='mail',
    casProxyEnabled=true, casProxyAllowAny=true, casProxyAllowList=''->
  hudson.security.cas.CasProtocol casProtocol = new hudson.security.cas.protocols.Cas20Protocol(
    casAuthoritiesAttribute,
    casFullNameAttribute,
    casEmailAttribute,
    casProxyEnabled,
    casProxyAllowAny,
    casProxyAllowList
  )
  hudson.security.SecurityRealm cas_realm = new hudson.security.cas.CasSecurityRealm(
    casServerUrl,
    casProtocol,
    forceRenewal,
    enableSingleSignOut
  )
  instance.setSecurityRealm(cas_realm)
  println "Setting CAS security realm"
}
