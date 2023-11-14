# This script generates and updates project configuration files.

# Run this script with rvscaffold in PYTHONPATH
import rvscaffold as scaffold

class Project(scaffold.Java):
    def script_path_text(self): return __file__
    def repository_name(self): return 'sourceafis-visualization-java'
    def pretty_name(self): return 'SourceAFIS Visualization for Java'
    def pom_name(self): return 'SourceAFIS Visualization'
    def pom_description(self): return 'Visualizations of SourceAFIS templates and algorithm transparency data.'
    def inception_year(self): return 2018
    def homepage(self): return self.website() + 'transparency/'
    def jdk_version(self): return 17
    def has_website(self): return False
    def has_javadoc(self): return False
    def stagean_annotations(self): return True
    def md_description(self): return '''\
        SourceAFIS Visualization for Java is a library that generates bitmap and vector images
        representing [fingerprint templates](https://sourceafis.machinezoo.com/template)
        and [algorithm transparency](https://sourceafis.machinezoo.com/transparency/) data
        produced by [SourceAFIS](https://sourceafis.machinezoo.com/) fingerprint recognition engine.
        This is a Java library, but it can process templates and transparency data from any of the language ports of SourceAFIS.
    '''
    
    def documentation_links(self):
        yield 'SourceAFIS algorithm transparency', self.homepage()
    
    def dependencies(self):
        yield from super().dependencies()
        yield self.use('com.machinezoo.sourceafis:sourceafis-transparency:0.13.1')
        yield self.use_streamex()
        yield self.use_fastutil()
        yield self.use_commons_lang()
        # Lazy way to get convenient SVG DOM builders. Should use dedicated SVG library in the future.
        yield self.use_pushmode()
        yield self.use_junit()
        yield self.use_hamcrest()
        yield self.use_slf4j_test()
    
    def javadoc_links(self):
        yield from super().javadoc_links()
        yield 'https://sourceafis.machinezoo.com/javadoc/'
        # Transparency library does not have javadoc yet.

Project().generate()
