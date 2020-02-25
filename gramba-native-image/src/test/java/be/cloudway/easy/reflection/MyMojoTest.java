package be.cloudway.easy.reflection;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

public class MyMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };



    /**
     * @throws Exception if any
     */
    @Test
    public void testSomething()
            throws Exception {

    }
//        File pom = new File( "target/test-classes/project-to-test/" );
//        assertNotNull( pom );
//        assertTrue( pom.exists() );
//
//        ReflectionGrabber reflectionGrabber = (ReflectionGrabber) rule.lookupConfiguredMojo( pom, "touch" );
//        assertNotNull(reflectionGrabber);
//        reflectionGrabber.execute();
//
//        File outputDirectory = ( File ) rule.getVariableValueFromObject(reflectionGrabber, "outputDirectory" );
//        assertNotNull( outputDirectory );
//        assertTrue( outputDirectory.exists() );
//
//        File touch = new File( outputDirectory, "touch.txt" );
//        assertTrue( touch.exists() );
}

