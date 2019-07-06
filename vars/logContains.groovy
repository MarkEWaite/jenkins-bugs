import com.markwaite.Assert

def call(Map config) {
    def my_check = new com.markwaite.Assert()
    my_check.logContains(config.expectedRegEx, config.failureMessage)
}