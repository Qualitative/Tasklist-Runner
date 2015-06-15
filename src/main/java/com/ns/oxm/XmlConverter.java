package com.ns.oxm;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ns.exception.ConvertException;
import com.ns.util.InputOutputUtils;

public class XmlConverter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(XmlConverter.class);

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XmlConverter(Marshaller marshaller, Unmarshaller unmarshaller) {
        this.marshaller = checkNotNull(marshaller);
        this.unmarshaller = checkNotNull(unmarshaller);
    }

    public void convertFromObjectToXml(T object, File file) throws ConvertException {
        LOG.debug("Converting object to XML file[{}]", file.getPath());
        try (Writer out = InputOutputUtils.createUnicodeWriter(file)) {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, new StreamResult(out));
        } catch (Exception e) {
            LOG.warn("Can't convert object to XML", e);
            throw new ConvertException("Can't convert object to XML", e);
        }
    }

    @SuppressWarnings("unchecked")
    public T convertFromXmlToObject(File file) throws ConvertException {
        LOG.debug("Converting XML file [{}] to object", file.getPath());
        try (Reader in = InputOutputUtils.createUnicodeReader(file)) {
            return (T) unmarshaller.unmarshal(new StreamSource(in));
        } catch (Exception e) {
            LOG.warn("Can't convert XML file to object", e);
            throw new ConvertException("Can't convert XML file to object", e);
        }
    }

    @SuppressWarnings("rawtypes")
    public void convertFromListToXml(List<T> list, File file, String name) throws ConvertException {
        LOG.debug("Converting list to XML file[{}] with root element name [{}]", file.getPath(), name);
        QName qName = new QName(name);
        Wrapper<T> wrapper = new Wrapper<T>(list);
        JAXBElement<Wrapper> jaxbElement = new JAXBElement<Wrapper>(qName, Wrapper.class, wrapper);
        try (Writer out = InputOutputUtils.createUnicodeWriter(file)) {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(jaxbElement, new StreamResult(out));
        } catch (Exception e) {
            LOG.warn("Can't convert list to XML", e);
            throw new ConvertException("Can't convert list to XML", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> convertFromXmlToList(File file) throws ConvertException {
        LOG.debug("Converting XML file [{}] to list", file.getPath());
        try (Reader in = InputOutputUtils.createUnicodeReader(file)) {
            Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(new StreamSource(in), Wrapper.class).getValue();
            return wrapper.getItems();
        } catch (Exception e) {
            LOG.warn("Can't convert XML file to list", e);
            throw new ConvertException("Can't convert XML file to list", e);
        }

    }

}
