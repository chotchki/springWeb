package us.henge.utility.security.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.context.VariablesMap;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import us.henge.utility.security.KeyCreation;
import us.henge.utility.security.ProtectedUrlFilter;

public class AbstractProtectProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor{
	private static final Logger log = LoggerFactory.getLogger(AbstractProtectProcessor.class);
	
	private final String ATTR;
	
    public AbstractProtectProcessor(String attrName) {
    	super(attrName);
    	ATTR = attrName;
    }
    
	@Override
	public int getPrecedence() {
		return 0;
	}

	@Override
	protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
		return ATTR;
	}
	
    @Override
    protected String getTargetAttributeValue(final Arguments arguments, final Element element, final String attributeName){
    	String value = super.getTargetAttributeValue(arguments, element, attributeName);
    	
    	try {
    		@SuppressWarnings("unchecked")
			VariablesMap<String, Object> vm = (VariablesMap<String, Object>) arguments.getContext().getVariables().get("session");
    		return ProtectedUrlFilter.HANDLER + "/" + KeyCreation.encrypt(vm, value);
    	} catch(Exception e){
    		log.warn("Had an error during encryption", e);
    		return "";
    	}
	}

	@Override
	protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return ModificationType.SUBSTITUTION;
	}

	@Override
	protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return false;
	}
}
