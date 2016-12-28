package io.app.web.util;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;

import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
/**
 * 
 * Classe qui permet de rendre les param�tres de pagination Spring interpretable par swagger
 * @link https://github.com/springfox/springfox/issues/755
 * 
 * @author svvbqs
 *
 */
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
    ModelContext modelContext = inputParam(context.resolvedMethodParameter().getResolvedParameterType(),
        context.getDocumentationType(),
        context.getAlternateTypeProvider(),
        context.getGenericNamingStrategy());
    return modelRefFactory(modelContext, nameExtractor);
  }

  @Override
  public void apply(ParameterContext context) {
    MethodParameter parameter = context.methodParameter();
    Class<?> type = parameter.getParameterType();
    if (type != null && Pageable.class.isAssignableFrom(type)) {
      Function<ResolvedType, ? extends ModelReference> factory =
          createModelRefFactory(context);

      ModelReference intModel = factory.apply(resolver.resolve(Integer.TYPE));
      ModelReference stringModel = factory.apply(resolver.resolve(List.class, String.class));

      List<Parameter> parameters = newArrayList(
          context.parameterBuilder()
              .parameterType("query").name("page").modelRef(intModel)
              .description("Results page you want to retrieve (0..N)")
              .build(),
          context.parameterBuilder()
              .parameterType("query").name("size").modelRef(intModel)
              .description("Number of recods per page")
              .build(),
          context.parameterBuilder()
              .parameterType("query").name("sort").modelRef(stringModel).allowMultiple(true)
              .description("Sorting criteria in the format: property(,asc|desc). "
                  + "Default sort order is ascending. "
                  + "Multiple sort criteria are supported.")
              .build());

      context.getOperationContext().operationBuilder().parameters(parameters);
    }
  }

}
