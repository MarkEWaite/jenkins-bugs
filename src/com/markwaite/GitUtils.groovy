package com.markwaite;

def adjustRemoteConfig(systemConfig, branch) {
  // Major time and bandwidth savings by narrowing refspec to single branch
  if (!systemConfig.refspec.contains(branch)) {
    systemConfig.refspec = "+refs/heads/${branch}:refs/remotes/${systemConfig.name}/${branch}"
  }
  // If inside known network, use caching server as first reference for faster access
  if (env.JENKINS_URL.contains("markwaite.net") && !systemConfig.url.contains("markwaite.net")) {
    def cacheConfig = [name: 'git-markwaite-net',
                       refspec: "+refs/heads/${branch}:refs/remotes/git-markwaite-net/${branch}",
                       credentialsId: 'mwaite-mark-pc1-rsa-private-key',
                       url: 'mwaite@git.markwaite.net:git/bare/bugs/jenkins-bugs.git']
    // Refer to cache config before system config to reduce wide area network use
    return [ cacheConfig, systemConfig ]
  }
  return [ systemConfig ]
}
