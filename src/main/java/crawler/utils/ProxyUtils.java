package crawler.utils;

/**
 * Created by matvii on 11.04.17.
 */

import crawler.web.proxy.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by matvii on 11.04.17.
 */

public class ProxyUtils {

	private static final Logger logger = LoggerFactory.getLogger(ProxyUtils.class);

	public static boolean validateProxy(Proxy p) {
		Socket socket = null;
		try {
			socket = new Socket();
			InetSocketAddress endpointSocketAddr = new InetSocketAddress(p.getHost(), p.getPort());
			socket.connect(endpointSocketAddr, 3000);
			return true;
		} catch (IOException e) {
			logger.warn("failed to connect to remote: " + p);
			return false;
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					logger.warn("Error while closing sockets", e);
				}
			}
		}

	}

}
