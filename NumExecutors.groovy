// Set the global number of master executor slots
def num_executors = { instance, num ->
  instance.setNumExecutors(num)
  println "Setting executors to ${num}"
}
