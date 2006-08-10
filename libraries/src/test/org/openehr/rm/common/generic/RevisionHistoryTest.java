/*
 * RevisionHistoryTest.java
 * JUnit based test
 *
 * Created on July 18, 2006, 10:43 PM
 */

package org.openehr.rm.common.generic;

import java.util.ArrayList;
import junit.framework.*;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.openehr.rm.RMObject;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.TestCodePhrase;
import org.openehr.rm.support.identification.HierarchicalObjectID;
import org.openehr.rm.support.identification.ObjectReference;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.PartyReference;
import org.openehr.rm.support.identification.TestTerminologyID;
import org.openehr.rm.support.terminology.TestTerminologyService;

/**
 *
 * @author yinsulim
 */
public class RevisionHistoryTest extends TestCase {
    
    public RevisionHistoryTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        PartyReference pr = new PartyReference(new HierarchicalObjectID("1-2-3-4-5"), 
                ObjectReference.Type.PARTY);
        PartyIdentified pi = new PartyIdentified(pr, "party name", null);
        CodePhrase codePhrase =
                new CodePhrase(TestTerminologyID.SNOMEDCT, "revisionCode");
        DvCodedText codedText = new DvCodedText("code value", TestCodePhrase.ENGLISH,
                TestCodePhrase.LATIN_1, codePhrase,
                TestTerminologyService.getInstance());
        AuditDetails audit1 = new AuditDetails("12.3.4.5", pi, 
                new DvDateTime("2006-05-01T10:10:00"), codedText, null,
                TestTerminologyService.getInstance());
        AuditDetails audit2 = new AuditDetails("20.3.33.55", pi, 
                new DvDateTime("2006-06-01T10:10:00"), codedText, null,
                TestTerminologyService.getInstance());
        List<AuditDetails> audits1 = new ArrayList<AuditDetails>();
        audits1.add(audit1);
        List<AuditDetails> audits2 = new ArrayList<AuditDetails>();
        audits2.add(audit2);
        RevisionHistoryItem rhi1 = new RevisionHistoryItem(audits1, 
                new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::123::1"));
        RevisionHistoryItem rhi2 = new RevisionHistoryItem(audits2, 
                new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::123::2"));
        List<RevisionHistoryItem> rhiList = new ArrayList<RevisionHistoryItem>();
        rhiList.add(rhi1);
        rhiList.add(rhi2);
        rh = new RevisionHistory(rhiList);
        
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(RevisionHistoryTest.class);
        
        return suite;
    }


    public void testMostRecentVersionId() {
        assertEquals("1.4.4.5::1.2.840.114.1.2.2::123::2", rh.mostRecentVersionId());
    }

    public void testMostRecentVersionTime() {
        assertEquals("2006-06-01T10:10:00", rh.mostRecentVersionTime());
    }

    private RevisionHistory rh;
}