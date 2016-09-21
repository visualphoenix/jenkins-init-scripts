// Configure extended email
def extendedEmail = { instance ->
  def extendedEmailPublisher = instance.getDescriptor('hudson.plugins.emailext.ExtendedEmailPublisher')
  extendedEmailPublisher.upgradeFromMailer()
  extendedEmailPublisher.save()
  println "Setting extended email"
}
