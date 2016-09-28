// Configure global CAS security realm
def cas = { instance, casServerUrl,
            forceRenewal=false, enableSingleSignOut=true,
            casAuthoritiesAttribute="groups,roles", casFullNameAttribute='cn',
            casEmailAttribute='mail',
            casProxyEnabled=true, casProxyAllowAny=true, casProxyAllowList='' ->
  def casProtocol = new org.jenkinsci.plugins.cas.protocols.Cas20Protocol(
    casAuthoritiesAttribute,
    casFullNameAttribute,
    casEmailAttribute,
    casProxyEnabled,
    casProxyAllowAny,
    casProxyAllowList
  )
  def cas_realm = new org.jenkinsci.plugins.cas.CasSecurityRealm(
    casServerUrl,
    casProtocol,
    forceRenewal,
    enableSingleSignOut
  )
  instance.setSecurityRealm(cas_realm)
  println "Setting CAS security realm"
}
