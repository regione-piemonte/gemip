
package it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor;

/*-
 * Copyright © 2025 Regione Piemonte
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://interoperable-europe.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 * or
 * https://opensource.org/license/EUPL-1.2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fault_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault");
    private final static QName _Fault1_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault1");
    private final static QName _Fault2_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault2");
    private final static QName _Fault3_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault3");
    private final static QName _Fault4_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault4");
    private final static QName _Fault5_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault5");
    private final static QName _Fault6_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault6");
    private final static QName _Fault7_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault7");
    private final static QName _Fault8_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault8");
    private final static QName _Fault9_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault9");
    private final static QName _Fault10_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault10");
    private final static QName _Fault11_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault11");
    private final static QName _Fault12_QNAME = new QName("http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", "fault12");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TestResources }
     * 
     */
    public TestResources createTestResources() {
        return new TestResources();
    }

    /**
     * Create an instance of {@link TestResourcesResponse }
     * 
     */
    public TestResourcesResponse createTestResourcesResponse() {
        return new TestResourcesResponse();
    }

    /**
     * Create an instance of {@link CSIException }
     * 
     */
    public CSIException createCSIException() {
        return new CSIException();
    }

    /**
     * Create an instance of {@link SelfCheck }
     * 
     */
    public SelfCheck createSelfCheck() {
        return new SelfCheck();
    }

    /**
     * Create an instance of {@link CalledResource }
     * 
     */
    public CalledResource createCalledResource() {
        return new CalledResource();
    }

    /**
     * Create an instance of {@link SelfCheckResponse }
     * 
     */
    public SelfCheckResponse createSelfCheckResponse() {
        return new SelfCheckResponse();
    }

    /**
     * Create an instance of {@link InvocationNode }
     * 
     */
    public InvocationNode createInvocationNode() {
        return new InvocationNode();
    }

    /**
     * Create an instance of {@link HasSelfCheck }
     * 
     */
    public HasSelfCheck createHasSelfCheck() {
        return new HasSelfCheck();
    }

    /**
     * Create an instance of {@link HasSelfCheckResponse }
     * 
     */
    public HasSelfCheckResponse createHasSelfCheckResponse() {
        return new HasSelfCheckResponse();
    }

    /**
     * Create an instance of {@link IdentificaUserPassword }
     * 
     */
    public IdentificaUserPassword createIdentificaUserPassword() {
        return new IdentificaUserPassword();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordResponse }
     * 
     */
    public IdentificaUserPasswordResponse createIdentificaUserPasswordResponse() {
        return new IdentificaUserPasswordResponse();
    }

    /**
     * Create an instance of {@link Identita }
     * 
     */
    public Identita createIdentita() {
        return new Identita();
    }

    /**
     * Create an instance of {@link SystemException }
     * 
     */
    public SystemException createSystemException() {
        return new SystemException();
    }

    /**
     * Create an instance of {@link UnrecoverableException }
     * 
     */
    public UnrecoverableException createUnrecoverableException() {
        return new UnrecoverableException();
    }

    /**
     * Create an instance of {@link InternalException }
     * 
     */
    public InternalException createInternalException() {
        return new InternalException();
    }

    /**
     * Create an instance of {@link AuthException }
     * 
     */
    public AuthException createAuthException() {
        return new AuthException();
    }

    /**
     * Create an instance of {@link IdProviderNotFoundException }
     * 
     */
    public IdProviderNotFoundException createIdProviderNotFoundException() {
        return new IdProviderNotFoundException();
    }

    /**
     * Create an instance of {@link MalformedUsernameException }
     * 
     */
    public MalformedUsernameException createMalformedUsernameException() {
        return new MalformedUsernameException();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPIN }
     * 
     */
    public IdentificaUserPasswordPIN createIdentificaUserPasswordPIN() {
        return new IdentificaUserPasswordPIN();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPINResponse }
     * 
     */
    public IdentificaUserPasswordPINResponse createIdentificaUserPasswordPINResponse() {
        return new IdentificaUserPasswordPINResponse();
    }

    /**
     * Create an instance of {@link FindUseCasesForPersonaInApplication }
     * 
     */
    public FindUseCasesForPersonaInApplication createFindUseCasesForPersonaInApplication() {
        return new FindUseCasesForPersonaInApplication();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link FindUseCasesForPersonaInApplicationResponse }
     * 
     */
    public FindUseCasesForPersonaInApplicationResponse createFindUseCasesForPersonaInApplicationResponse() {
        return new FindUseCasesForPersonaInApplicationResponse();
    }

    /**
     * Create an instance of {@link UseCase }
     * 
     */
    public UseCase createUseCase() {
        return new UseCase();
    }

    /**
     * Create an instance of {@link IdentitaNonAutenticaException }
     * 
     */
    public IdentitaNonAutenticaException createIdentitaNonAutenticaException() {
        return new IdentitaNonAutenticaException();
    }

    /**
     * Create an instance of {@link NoSuchApplicationException }
     * 
     */
    public NoSuchApplicationException createNoSuchApplicationException() {
        return new NoSuchApplicationException();
    }

    /**
     * Create an instance of {@link FindUseCasesForPersonaRoleInApplication }
     * 
     */
    public FindUseCasesForPersonaRoleInApplication createFindUseCasesForPersonaRoleInApplication() {
        return new FindUseCasesForPersonaRoleInApplication();
    }

    /**
     * Create an instance of {@link FindUseCasesForPersonaRoleInApplicationResponse }
     * 
     */
    public FindUseCasesForPersonaRoleInApplicationResponse createFindUseCasesForPersonaRoleInApplicationResponse() {
        return new FindUseCasesForPersonaRoleInApplicationResponse();
    }

    /**
     * Create an instance of {@link BadRuoloException }
     * 
     */
    public BadRuoloException createBadRuoloException() {
        return new BadRuoloException();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInApplication }
     * 
     */
    public FindActorsForPersonaInApplication createFindActorsForPersonaInApplication() {
        return new FindActorsForPersonaInApplication();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInApplicationResponse }
     * 
     */
    public FindActorsForPersonaInApplicationResponse createFindActorsForPersonaInApplicationResponse() {
        return new FindActorsForPersonaInApplicationResponse();
    }

    /**
     * Create an instance of {@link Actor }
     * 
     */
    public Actor createActor() {
        return new Actor();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInUseCase }
     * 
     */
    public FindActorsForPersonaInUseCase createFindActorsForPersonaInUseCase() {
        return new FindActorsForPersonaInUseCase();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInUseCaseResponse }
     * 
     */
    public FindActorsForPersonaInUseCaseResponse createFindActorsForPersonaInUseCaseResponse() {
        return new FindActorsForPersonaInUseCaseResponse();
    }

    /**
     * Create an instance of {@link NoSuchUseCaseException }
     * 
     */
    public NoSuchUseCaseException createNoSuchUseCaseException() {
        return new NoSuchUseCaseException();
    }

    /**
     * Create an instance of {@link IsIdentitaAutentica }
     * 
     */
    public IsIdentitaAutentica createIsIdentitaAutentica() {
        return new IsIdentitaAutentica();
    }

    /**
     * Create an instance of {@link IsIdentitaAutenticaResponse }
     * 
     */
    public IsIdentitaAutenticaResponse createIsIdentitaAutenticaResponse() {
        return new IsIdentitaAutenticaResponse();
    }

    /**
     * Create an instance of {@link GetInfoPersonaSchema }
     * 
     */
    public GetInfoPersonaSchema createGetInfoPersonaSchema() {
        return new GetInfoPersonaSchema();
    }

    /**
     * Create an instance of {@link Ruolo }
     * 
     */
    public Ruolo createRuolo() {
        return new Ruolo();
    }

    /**
     * Create an instance of {@link GetInfoPersonaSchemaResponse }
     * 
     */
    public GetInfoPersonaSchemaResponse createGetInfoPersonaSchemaResponse() {
        return new GetInfoPersonaSchemaResponse();
    }

    /**
     * Create an instance of {@link FindElencoMenuDiApplicativoForUtenteInRuolo }
     * 
     */
    public FindElencoMenuDiApplicativoForUtenteInRuolo createFindElencoMenuDiApplicativoForUtenteInRuolo() {
        return new FindElencoMenuDiApplicativoForUtenteInRuolo();
    }

    /**
     * Create an instance of {@link FindElencoMenuDiApplicativoForUtenteInRuoloResponse }
     * 
     */
    public FindElencoMenuDiApplicativoForUtenteInRuoloResponse createFindElencoMenuDiApplicativoForUtenteInRuoloResponse() {
        return new FindElencoMenuDiApplicativoForUtenteInRuoloResponse();
    }

    /**
     * Create an instance of {@link MenuDiApplicativo }
     * 
     */
    public MenuDiApplicativo createMenuDiApplicativo() {
        return new MenuDiApplicativo();
    }

    /**
     * Create an instance of {@link FindElencoMenuDiApplicativoForUtenteDelegato }
     * 
     */
    public FindElencoMenuDiApplicativoForUtenteDelegato createFindElencoMenuDiApplicativoForUtenteDelegato() {
        return new FindElencoMenuDiApplicativoForUtenteDelegato();
    }

    /**
     * Create an instance of {@link FindElencoMenuDiApplicativoForUtenteDelegatoResponse }
     * 
     */
    public FindElencoMenuDiApplicativoForUtenteDelegatoResponse createFindElencoMenuDiApplicativoForUtenteDelegatoResponse() {
        return new FindElencoMenuDiApplicativoForUtenteDelegatoResponse();
    }

    /**
     * Create an instance of {@link FindMessaggioByCodApplicativo }
     * 
     */
    public FindMessaggioByCodApplicativo createFindMessaggioByCodApplicativo() {
        return new FindMessaggioByCodApplicativo();
    }

    /**
     * Create an instance of {@link FindMessaggioByCodApplicativoResponse }
     * 
     */
    public FindMessaggioByCodApplicativoResponse createFindMessaggioByCodApplicativoResponse() {
        return new FindMessaggioByCodApplicativoResponse();
    }

    /**
     * Create an instance of {@link GetMessaggiBachecaByUtente }
     * 
     */
    public GetMessaggiBachecaByUtente createGetMessaggiBachecaByUtente() {
        return new GetMessaggiBachecaByUtente();
    }

    /**
     * Create an instance of {@link GetMessaggiBachecaByUtenteResponse }
     * 
     */
    public GetMessaggiBachecaByUtenteResponse createGetMessaggiBachecaByUtenteResponse() {
        return new GetMessaggiBachecaByUtenteResponse();
    }

    /**
     * Create an instance of {@link MessaggioBacheca }
     * 
     */
    public MessaggioBacheca createMessaggioBacheca() {
        return new MessaggioBacheca();
    }

    /**
     * Create an instance of {@link SetElencoMessaggioComeLetti }
     * 
     */
    public SetElencoMessaggioComeLetti createSetElencoMessaggioComeLetti() {
        return new SetElencoMessaggioComeLetti();
    }

    /**
     * Create an instance of {@link SetElencoMessaggioComeLettiResponse }
     * 
     */
    public SetElencoMessaggioComeLettiResponse createSetElencoMessaggioComeLettiResponse() {
        return new SetElencoMessaggioComeLettiResponse();
    }

    /**
     * Create an instance of {@link SetElencoMessaggioComeNonLetti }
     * 
     */
    public SetElencoMessaggioComeNonLetti createSetElencoMessaggioComeNonLetti() {
        return new SetElencoMessaggioComeNonLetti();
    }

    /**
     * Create an instance of {@link SetElencoMessaggioComeNonLettiResponse }
     * 
     */
    public SetElencoMessaggioComeNonLettiResponse createSetElencoMessaggioComeNonLettiResponse() {
        return new SetElencoMessaggioComeNonLettiResponse();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInUseCaseConcurrent }
     * 
     */
    public FindActorsForPersonaInUseCaseConcurrent createFindActorsForPersonaInUseCaseConcurrent() {
        return new FindActorsForPersonaInUseCaseConcurrent();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInUseCaseConcurrentResponse }
     * 
     */
    public FindActorsForPersonaInUseCaseConcurrentResponse createFindActorsForPersonaInUseCaseConcurrentResponse() {
        return new FindActorsForPersonaInUseCaseConcurrentResponse();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInApplicationConcurrent }
     * 
     */
    public FindActorsForPersonaInApplicationConcurrent createFindActorsForPersonaInApplicationConcurrent() {
        return new FindActorsForPersonaInApplicationConcurrent();
    }

    /**
     * Create an instance of {@link FindActorsForPersonaInApplicationConcurrentResponse }
     * 
     */
    public FindActorsForPersonaInApplicationConcurrentResponse createFindActorsForPersonaInApplicationConcurrentResponse() {
        return new FindActorsForPersonaInApplicationConcurrentResponse();
    }

    /**
     * Create an instance of {@link GetInfoPersonaInUseCaseConcurrent }
     * 
     */
    public GetInfoPersonaInUseCaseConcurrent createGetInfoPersonaInUseCaseConcurrent() {
        return new GetInfoPersonaInUseCaseConcurrent();
    }

    /**
     * Create an instance of {@link GetInfoPersonaInUseCaseConcurrentResponse }
     * 
     */
    public GetInfoPersonaInUseCaseConcurrentResponse createGetInfoPersonaInUseCaseConcurrentResponse() {
        return new GetInfoPersonaInUseCaseConcurrentResponse();
    }

    /**
     * Create an instance of {@link IdentificaCertificato }
     * 
     */
    public IdentificaCertificato createIdentificaCertificato() {
        return new IdentificaCertificato();
    }

    /**
     * Create an instance of {@link IdentificaCertificatoResponse }
     * 
     */
    public IdentificaCertificatoResponse createIdentificaCertificatoResponse() {
        return new IdentificaCertificatoResponse();
    }

    /**
     * Create an instance of {@link CertOutsideValidityException }
     * 
     */
    public CertOutsideValidityException createCertOutsideValidityException() {
        return new CertOutsideValidityException();
    }

    /**
     * Create an instance of {@link CertRevokedException }
     * 
     */
    public CertRevokedException createCertRevokedException() {
        return new CertRevokedException();
    }

    /**
     * Create an instance of {@link IsPersonaAutorizzataInUseCase }
     * 
     */
    public IsPersonaAutorizzataInUseCase createIsPersonaAutorizzataInUseCase() {
        return new IsPersonaAutorizzataInUseCase();
    }

    /**
     * Create an instance of {@link IsPersonaAutorizzataInUseCaseResponse }
     * 
     */
    public IsPersonaAutorizzataInUseCaseResponse createIsPersonaAutorizzataInUseCaseResponse() {
        return new IsPersonaAutorizzataInUseCaseResponse();
    }

    /**
     * Create an instance of {@link IsPersonaInRuolo }
     * 
     */
    public IsPersonaInRuolo createIsPersonaInRuolo() {
        return new IsPersonaInRuolo();
    }

    /**
     * Create an instance of {@link IsPersonaInRuoloResponse }
     * 
     */
    public IsPersonaInRuoloResponse createIsPersonaInRuoloResponse() {
        return new IsPersonaInRuoloResponse();
    }

    /**
     * Create an instance of {@link GetInfoPersonaInUseCase }
     * 
     */
    public GetInfoPersonaInUseCase createGetInfoPersonaInUseCase() {
        return new GetInfoPersonaInUseCase();
    }

    /**
     * Create an instance of {@link GetInfoPersonaInUseCaseResponse }
     * 
     */
    public GetInfoPersonaInUseCaseResponse createGetInfoPersonaInUseCaseResponse() {
        return new GetInfoPersonaInUseCaseResponse();
    }

    /**
     * Create an instance of {@link FindRuoliForPersonaInUseCase }
     * 
     */
    public FindRuoliForPersonaInUseCase createFindRuoliForPersonaInUseCase() {
        return new FindRuoliForPersonaInUseCase();
    }

    /**
     * Create an instance of {@link FindRuoliForPersonaInUseCaseResponse }
     * 
     */
    public FindRuoliForPersonaInUseCaseResponse createFindRuoliForPersonaInUseCaseResponse() {
        return new FindRuoliForPersonaInUseCaseResponse();
    }

    /**
     * Create an instance of {@link FindRuoliForPersonaInApplication }
     * 
     */
    public FindRuoliForPersonaInApplication createFindRuoliForPersonaInApplication() {
        return new FindRuoliForPersonaInApplication();
    }

    /**
     * Create an instance of {@link FindRuoliForPersonaInApplicationResponse }
     * 
     */
    public FindRuoliForPersonaInApplicationResponse createFindRuoliForPersonaInApplicationResponse() {
        return new FindRuoliForPersonaInApplicationResponse();
    }

    /**
     * Create an instance of {@link ArrayOfTns1InvocationNode }
     * 
     */
    public ArrayOfTns1InvocationNode createArrayOfTns1InvocationNode() {
        return new ArrayOfTns1InvocationNode();
    }

    /**
     * Create an instance of {@link UserException }
     * 
     */
    public UserException createUserException() {
        return new UserException();
    }

    /**
     * Create an instance of {@link CommunicationException }
     * 
     */
    public CommunicationException createCommunicationException() {
        return new CommunicationException();
    }

    /**
     * Create an instance of {@link Funzionalita }
     * 
     */
    public Funzionalita createFunzionalita() {
        return new Funzionalita();
    }

    /**
     * Create an instance of {@link ArrayOfFunzionalita }
     * 
     */
    public ArrayOfFunzionalita createArrayOfFunzionalita() {
        return new ArrayOfFunzionalita();
    }

    /**
     * Create an instance of {@link ResourceType }
     * 
     */
    public ResourceType createResourceType() {
        return new ResourceType();
    }

    /**
     * Create an instance of {@link Outcome }
     * 
     */
    public Outcome createOutcome() {
        return new Outcome();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CSIException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CSIException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault")
    public JAXBElement<CSIException> createFault(CSIException value) {
        return new JAXBElement<CSIException>(_Fault_QNAME, CSIException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SystemException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault1")
    public JAXBElement<SystemException> createFault1(SystemException value) {
        return new JAXBElement<SystemException>(_Fault1_QNAME, SystemException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnrecoverableException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UnrecoverableException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault2")
    public JAXBElement<UnrecoverableException> createFault2(UnrecoverableException value) {
        return new JAXBElement<UnrecoverableException>(_Fault2_QNAME, UnrecoverableException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternalException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link InternalException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault3")
    public JAXBElement<InternalException> createFault3(InternalException value) {
        return new JAXBElement<InternalException>(_Fault3_QNAME, InternalException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault4")
    public JAXBElement<AuthException> createFault4(AuthException value) {
        return new JAXBElement<AuthException>(_Fault4_QNAME, AuthException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdProviderNotFoundException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdProviderNotFoundException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault5")
    public JAXBElement<IdProviderNotFoundException> createFault5(IdProviderNotFoundException value) {
        return new JAXBElement<IdProviderNotFoundException>(_Fault5_QNAME, IdProviderNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MalformedUsernameException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MalformedUsernameException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault6")
    public JAXBElement<MalformedUsernameException> createFault6(MalformedUsernameException value) {
        return new JAXBElement<MalformedUsernameException>(_Fault6_QNAME, MalformedUsernameException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentitaNonAutenticaException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link IdentitaNonAutenticaException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault7")
    public JAXBElement<IdentitaNonAutenticaException> createFault7(IdentitaNonAutenticaException value) {
        return new JAXBElement<IdentitaNonAutenticaException>(_Fault7_QNAME, IdentitaNonAutenticaException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchApplicationException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoSuchApplicationException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault8")
    public JAXBElement<NoSuchApplicationException> createFault8(NoSuchApplicationException value) {
        return new JAXBElement<NoSuchApplicationException>(_Fault8_QNAME, NoSuchApplicationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BadRuoloException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BadRuoloException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault9")
    public JAXBElement<BadRuoloException> createFault9(BadRuoloException value) {
        return new JAXBElement<BadRuoloException>(_Fault9_QNAME, BadRuoloException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NoSuchUseCaseException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NoSuchUseCaseException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault10")
    public JAXBElement<NoSuchUseCaseException> createFault10(NoSuchUseCaseException value) {
        return new JAXBElement<NoSuchUseCaseException>(_Fault10_QNAME, NoSuchUseCaseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertOutsideValidityException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CertOutsideValidityException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault11")
    public JAXBElement<CertOutsideValidityException> createFault11(CertOutsideValidityException value) {
        return new JAXBElement<CertOutsideValidityException>(_Fault11_QNAME, CertOutsideValidityException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertRevokedException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CertRevokedException }{@code >}
     */
    @XmlElementDecl(namespace = "http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/", name = "fault12")
    public JAXBElement<CertRevokedException> createFault12(CertRevokedException value) {
        return new JAXBElement<CertRevokedException>(_Fault12_QNAME, CertRevokedException.class, null, value);
    }

}
