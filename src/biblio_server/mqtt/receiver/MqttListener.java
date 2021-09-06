package biblio_server.mqtt.receiver;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import biblio_server.messageBuilder.MessageManager;

public class MqttListener implements MqttCallback {

	MqttClient client;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MqttListener().start();
	}

	public void start() {
		try {
			client = new MqttClient("tcp://localhost:1883", "Sending", new MemoryPersistence());
			
			client.connect();
			client.setCallback(this);
			client.subscribe("library/received");

			
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(new String(message.getPayload()));
		new MessageManager().processData(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
}
