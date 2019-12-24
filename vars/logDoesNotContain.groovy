import com.markwaite.Assert

def call(Map config) {
    def my_check = new com.markwaite.Assert()
    my_check.logDoesNotContain(config.expectedRegEx, config.failureMessage)
}
