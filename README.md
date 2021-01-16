# [JENKINS-47824](https://issues.jenkins.io/browse/JENKINS-47824) - tagged shared pipeline library won't load

Git plugin 3.6.1 included optimizations that broke the loading of
a pipeline shared library if the library were being loaded by a tag.
My test kit clearly was lacking that case. It should be a standard part
of testing, since it is strongly recommended by Jessie Glick and others.
