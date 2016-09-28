// Configure global security realm
def ldap = { instance, ldap_addr, ldap_rootDN, ldap_managerDN, ldap_managerPass, ldap_userSearchBase='', ldap_userSearch='uid={0}', ldap_groupSearchBase='' ->
  def securityRealm = new hudson.security.LDAPSecurityRealm(
    server=ldap_addr,
    rootDN=ldap_rootDN,
    userSearchBase='',
    userSearch='uid={0}',
    groupSearchBase='',
    groupSearchFilter='',
    groupMembershipStrategy=null,
    managerDN=ldap_managerDN,
    managerPasswordSecret=hudson.util.Secret.fromString(ldap_managerPass),
    inhibitInferRootDN=false,
    disableMailAddressResolver=false,
    cache=null,
    environmentProperties=null,
    displayNameAttributeName='',
    mailAddressAttributeName='',
    userIdStrategy=null,
    groupIdStrategy=null
  )
  instance.setSecurityRealm(securityRealm)
  println "Setting LDAP Security Realm"
}
