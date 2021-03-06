package io.app.web.doc;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;
import io.app.web.util.SortableUri;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.base.Optional;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class PageableParameterBuilderPlugin implements ParameterBuilderPlugin {
    private final TypeNameExtractor nameExtractor;
    private final TypeResolver resolver;

    @Autowired
    public PageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
	this.nameExtractor = nameExtractor;
	this.resolver = resolver;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
	return true;
    }

    private Function<ResolvedType, ? extends ModelReference>
    createModelRefFactory(ParameterContext context) {
	final ModelContext modelContext = inputParam(context.resolvedMethodParameter().getParameterType(),
		context.getDocumentationType(),
		context.getAlternateTypeProvider(),
		context.getGenericNamingStrategy(),
		context.getIgnorableParameterTypes());
	return modelRefFactory(modelContext, nameExtractor);
    }

    @Override
    public void apply(ParameterContext context) {
	final ResolvedMethodParameter parameter = context.resolvedMethodParameter();
	final Class<?> type = parameter.getParameterType().getErasedType();
	if (type != null && Pageable.class.isAssignableFrom(type)) {
	    final Function<ResolvedType, ? extends ModelReference> factory =
		    createModelRefFactory(context);

	    final Optional<SortableUri> annotationSort = context.getOperationContext().findAnnotation(SortableUri.class);
	    String defaultDesc="Sorting criteria in the format: property(,asc|desc). "
		    + "Default sort order is ascending. "
		    + "Multiple sort criteria are supported.";
	    if(annotationSort.isPresent()){
		defaultDesc=Arrays.asList(annotationSort.get().allowableValues()).stream().collect(Collectors.joining());
		System.out.println(annotationSort.get().pageable().getTriAllowableValues());
	    }
	    final ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
	    final ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));
	    final List<Parameter> parameters = newArrayList(
		    context.parameterBuilder()
		    .parameterType("query").name("page").modelRef(intModel)
		    .description("Page number of the requested page")
		    .required(false)
		    .build(),
		    context.parameterBuilder()
		    .parameterType("query").name("size").modelRef(intModel)
		    .description("Size of a page")
		    .required(false)
		    .build(),
		    context.parameterBuilder()
		    .parameterType("query").name("sort").modelRef(stringModel).allowMultiple(true)
		    .description(defaultDesc)
		    .required(false)
		    .build());

	    context.getOperationContext().operationBuilder().parameters(parameters);
	}
    }

}