package com.touch.tests.IQHandler;

import com.touch.tests.extensions.OfferAccept;
import com.touch.tests.extensions.OfferProposal;
import com.touch.utils.StringUtils;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.stanza.IQHandler;
import rocks.xmpp.core.stanza.model.IQ;

/**
 * Created by oshcherbatyy on 07-09-17.
 */
public class OfferIQHandler implements IQHandler{
    public OfferProposal offerProposal;

    @Override
    public IQ handleRequest(IQ iq) {
        System.out.println("########################OFFER PROCESSED##################");
        offerProposal = iq.getExtension(OfferProposal.class);
        Jid myJid = iq.getTo();
        return new IQ(Jid.of("clickatell@department.clickatelllabs.com"),
                IQ.Type.SET, new OfferAccept(offerProposal.getId()),
                "cf314085-850f-e868-6401-90ee5714e" + StringUtils.generateRandomString(3),
                myJid,
                null,
                null);
    }

}
