// Set the global quiet period
def quiet_period = { instance, period ->
  instance.setQuietPeriod(period)
  println "Setting quiet period to ${period}"
}
