# This script generates and updates project configuration files.

# We are assuming that project-config is available in sibling directory.
# Checkout from https://github.com/robertvazan/project-config
import pathlib
project_directory = lambda: pathlib.Path(__file__).parent.parent
config_directory = lambda: project_directory().parent/'project-config'
exec((config_directory()/'src'/'java.py').read_text())

project_script_path = __file__
repository_name = lambda: 'sourceafis-visualization-java'
pretty_name = lambda: 'SourceAFIS Visualization for Java'
pom_subgroup = lambda: 'sourceafis'
pom_artifact = lambda: 'sourceafis-visualization'
pom_name = lambda: 'SourceAFIS Visualization'
pom_description = lambda: 'Visualizations of SourceAFIS templates and algorithm transparency data.'
inception_year = lambda: 2018
homepage = lambda: website() + 'transparency/'
jdk_version = lambda: 17
has_website = lambda: False
has_javadoc = lambda: False
stagean_annotations = lambda: True
project_status = lambda: experimental_status()
md_description = lambda: '''\
    SourceAFIS Visualization for Java is a library that generates bitmap and vector images
    representing [fingerprint templates](https://sourceafis.machinezoo.com/template)
    and [algorithm transparency](https://sourceafis.machinezoo.com/transparency/) data
    produced by [SourceAFIS](https://sourceafis.machinezoo.com/) fingerprint recognition engine.
    This is a Java library, but it can process templates and transparency data from any of the language ports of SourceAFIS.
'''

def documentation_links():
    yield 'SourceAFIS algorithm transparency', homepage()

def dependencies():
    use('com.machinezoo.sourceafis:sourceafis-transparency:0.11.0')
    use_streamex()
    use_fastutil()
    use_commons_lang()
    # Lazy way to get convenient SVG DOM builders. Should use dedicated SVG library in the future.
    use_pushmode()
    use_junit()
    use_hamcrest()
    use_slf4j_test()

javadoc_links = lambda: [
    'https://sourceafis.machinezoo.com/javadoc/',
    # Transparency library does not have javadoc yet.
]

generate()
