package edu.school21.annotations.Process;

import com.google.auto.service.AutoService;
import edu.school21.annotations.Annotations.HtmlForm;
import edu.school21.annotations.Annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes("edu.school21.annotations.Annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_18)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            List<? extends Element> elements = element.getEnclosedElements();
            List<Annotation> fields = new ArrayList<>(elements.size());
            for(Element currentElement : elements) {
                if(currentElement.getAnnotation(HtmlInput.class) != null) {
                    Annotation field = currentElement.getAnnotation(HtmlInput.class);
                    fields.add(field);
                }
            }
            try {
                creatingHTML(element.getAnnotation(HtmlForm.class), fields);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    private void creatingHTML(HtmlForm form, List<Annotation> fields) throws IOException {
        FileObject fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT,
                "", form.fileName());
        try(FileWriter writer = new FileWriter(fileObject.toUri().getPath()))
        {
            String formAction = "<form action = \"" + form.action() + "\" method = " + form.method() + "\">\n";
            writer.write(formAction);
            for (Annotation field : fields) {
                HtmlInput input = (HtmlInput)field;
                String inputStr = "<input type = \"" + input.type() + "\" name = \"" + input.name() + "\" placeholder = \"" + input.placeholder()+ "\">\n";
                writer.write(inputStr);
            }
            writer.write("</form>");
            writer.flush();
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}

