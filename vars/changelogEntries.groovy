// changeSets argument usually is currentBuild.changeSets
//
// def entries = changeLogEntries(changeSets: currentBuild.changeSets)

def call(Map config) {
  def entriesList = []
  for (int i = 0; i < config.changeSets.size(); i++) {
    def entries = config.changeSets[i].items
    for (int j = 0; j < entries.length; j++) {
      entriesList.add(entries[j])
    }
  }
  return entriesList
}
