echo 'start run mvn dependency:copy-dependencies bat'
@set parm_1=%1
@set parm_2=%2
@set parm_3=%3

@rem set M2_HOME=D:\Program Files\apache-maven
@rem set JAVA_HOME=D:\Program Files\Java\jdk1.7.0_80
@rem "set project path, eg. D:\SpringRooWorkSpace\ump20170420_chery_pc"
@rem  set parm_pom_setting=E:\apache-maven-3.5.3\conf\settings.xml
echo "system will start set project path"
@if "%parm_1%"=="" (
	echo "your aren't set project pom path"  
) else (
	@rem  cd  %parm_1%
	echo "system start set maven setting.xml ,eg. E:\apache-maven-3.5.3\conf\settings.xml"
	@if "%parm_pom_setting%"=="" (
		echo "your aren't set maven setting path,we will use system default setting" 
		call mvn  -f %parm_1% dependency:copy-dependencies -DoutputDirectory=%parm_2%  -DincludeScope=%parm_3%
	) else ( 
		echo "maven setting path: %parm_pom_setting%"
		call mvn --settings %parm_pom_setting% -f %parm_1% dependency:copy-dependencies -DoutputDirectory=%parm_2%  -DincludeScope=%parm_3%

	)
)
exit