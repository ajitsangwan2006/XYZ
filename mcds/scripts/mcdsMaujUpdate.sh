#/bin/sh

echo "`date` Starting Phone Mapping Updation process comes from MAUJ"

# variables
source /home/vas/vas/setVasEnv.sh

	
cd $VAS_HOME/mcds/scripts
$VAS_HOME/mcds/scripts/mcdsRunapp.sh com.gisil.mcds.script.PhoneMappingJob -errfile1 $source

echo "`date` Done..."
echo "###########################################################################"
echo ""
echo ""

echo "`date` Starting Content Packs Updation process comes from MAUJ"

# variables
source /home/vas/vas/setVasEnv.sh

	
cd $VAS_HOME/mcds/scripts
$VAS_HOME/mcds/scripts/mcdsRunapp.sh com.gisil.mcds.script.ContentPackUpdateJob -errfile1 $source

echo "`date` Done..."
echo "###########################################################################"
echo ""
echo ""