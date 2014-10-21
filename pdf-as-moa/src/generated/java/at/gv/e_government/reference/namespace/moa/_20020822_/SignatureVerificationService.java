package at.gv.e_government.reference.namespace.moa._20020822_;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-10-21T07:59:06.766+02:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "SignatureVerificationService", 
                  wsdlLocation = "/wsdl/MOA-SPSS-1.5.2.wsdl",
                  targetNamespace = "http://reference.e-government.gv.at/namespace/moa/20020822#") 
public class SignatureVerificationService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://reference.e-government.gv.at/namespace/moa/20020822#", "SignatureVerificationService");
    public final static QName SignatureVerificationPort = new QName("http://reference.e-government.gv.at/namespace/moa/20020822#", "SignatureVerificationPort");
    static {
        URL url = SignatureVerificationService.class.getResource("/wsdl/MOA-SPSS-1.5.2.wsdl");
        if (url == null) {
            url = SignatureVerificationService.class.getClassLoader().getResource("/wsdl/MOA-SPSS-1.5.2.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(SignatureVerificationService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "/wsdl/MOA-SPSS-1.5.2.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public SignatureVerificationService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SignatureVerificationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SignatureVerificationService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SignatureVerificationService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SignatureVerificationService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public SignatureVerificationService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    

    /**
     *
     * @return
     *     returns SignatureVerificationPortType
     */
    @WebEndpoint(name = "SignatureVerificationPort")
    public SignatureVerificationPortType getSignatureVerificationPort() {
        return super.getPort(SignatureVerificationPort, SignatureVerificationPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SignatureVerificationPortType
     */
    @WebEndpoint(name = "SignatureVerificationPort")
    public SignatureVerificationPortType getSignatureVerificationPort(WebServiceFeature... features) {
        return super.getPort(SignatureVerificationPort, SignatureVerificationPortType.class, features);
    }

}
