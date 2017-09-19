package com.touch.tests.xmppdebugger;

import com.touch.tests.XMPPAgent;
import org.slf4j.LoggerFactory;
import org.testng.log4testng.Logger;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.session.debug.XmppDebugger;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by oshcherbatyy on 19-09-17.
 */
public class ConsoleXmppLogger implements XmppDebugger {

    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public void initialize(XmppSession xmppSession) {
    }

    @Override
    public void writeStanza(String xml, Object stanza) {
        LOG.info("STANZA OUT: " + xml);
    }

    @Override
    public void readStanza(String xml, Object stanza) {
        LOG.info(" STANZA IN : " + xml);
    }

    @Override
    public OutputStream createOutputStream(OutputStream outputStream) {
        return outputStream;
    }

    @Override
    public InputStream createInputStream(InputStream inputStream) {
        return inputStream;
    }
}
