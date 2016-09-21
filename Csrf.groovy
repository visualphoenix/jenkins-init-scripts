// Configure global CSRF protection
def csrf = { instance, enable_csrf=true ->
  instance.setCrumbIssuer(new hudson.security.csrf.DefaultCrumbIssuer(enable_csrf))
  println 'Enabled CSRF Protection'
}
