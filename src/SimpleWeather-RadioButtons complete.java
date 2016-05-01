//**************************************************************************************************

// 211 Final Project				Ben Miller, Jason, Dorothy

//

// Gui for SimpleWeather

//**************************************************************************************************

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.net.*;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Locale;
import java.io.IOException;

public class SimpleWeather extends JFrame
{

	private static final long serialVersionUID = 1L;
	private boolean isMetric;
	private JFrame frame;
	private JPanel contents, panelCenter, panelNorth, panelSouth;
	private Border borderContents = BorderFactory.createEmptyBorder(10, 10, 10, 10);
	private Border borderRegion = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Color colorContents = Color.BLUE;
	private Font fontLabels = new Font(Font.DIALOG, Font.BOLD, 16);
	private JTextField enterZip;
	private JButton getWeather, usMetric;
	private JLabel highTemp, lowTemp, humid;
	private JLabel howMuchRain, date;
	private JLabel temp, cloud, wind, zip;
	private JRadioButton US, Metric;

	public SimpleWeather()
	{
		isMetric = false;
		
		// create main panel
		setFonts();

		contents = new JPanel(new GridBagLayout());

		contents.setLayout(new BorderLayout());
		contents.setBorder(borderContents);
		contents.setBackground(colorContents);

		setContentPane(contents);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		
		// create center panel and add labels
		panelCenter = new JPanel(new GridBagLayout());
		panelCenter.setPreferredSize(new Dimension(800, 300));
		contents.add(panelCenter, BorderLayout.CENTER);
		
		// add these using a method, because we don't need to refer to them again
		placeLabel(panelCenter, gbc, "Temperature", 0);
		placeLabel(panelCenter, gbc, "High", 1);
		placeLabel(panelCenter, gbc, "Low", 2);
		placeLabel(panelCenter, gbc, "Humidity", 3);
		placeLabel(panelCenter, gbc, "Chance of Precipitation", 4);
		placeLabel(panelCenter, gbc, "Cloud Cover", 5);
		placeLabel(panelCenter, gbc, "Wind Speed", 6);

		// we do need these ones
		temp = new JLabel("Hot");
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelCenter.add(temp, gbc);

		highTemp = new JLabel("90");
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelCenter.add(highTemp, gbc);

		lowTemp = new JLabel("Zero");
		gbc.gridx = 1;
		gbc.gridy = 2;
		panelCenter.add(lowTemp, gbc);

		humid = new JLabel("Super humid");
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelCenter.add(humid, gbc);

		howMuchRain = new JLabel("Lots of Rain");
		gbc.gridx = 1;
		gbc.gridy = 4;
		panelCenter.add(howMuchRain, gbc);

		cloud = new JLabel("super cloudy ");
		gbc.gridx = 1;
		gbc.gridy = 5;
		panelCenter.add(cloud, gbc);

		wind = new JLabel("no wind ");
		gbc.gridx = 1;
		gbc.gridy = 6;
		panelCenter.add(wind, gbc);

		
		// create top panel and add title label to it
		panelNorth = new JPanel();
		panelNorth.setBorder(borderRegion);
		panelNorth.setPreferredSize(new Dimension(500, 50));
		
		panelNorth.add(new JLabel("               "));
		panelNorth.add(new JLabel("                         "));
		panelNorth.add(new JLabel("SimpleWeather"));

		panelNorth.add(new JLabel("                         "));

		date = new JLabel("March 2nd");
		panelNorth.add(date);
		contents.add(panelNorth, BorderLayout.NORTH);

		
		// create bottom panel and add labels
		panelSouth = new JPanel();
		panelSouth.setBorder(borderRegion);
		panelSouth.setPreferredSize(new Dimension(500, 100));
		contents.add(panelSouth, BorderLayout.SOUTH);
		
		
		placeLabel(panelSouth, gbc, "Enter your ZIP code", 0);

		enterZip = new JTextField(5);
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelSouth.add(enterZip, gbc);

		getWeather = new JButton("Get Weather Info");
		gbc.gridx = 2;
		gbc.gridy = 0;
		panelSouth.add(getWeather, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		panelSouth.add(new JLabel("                             "), gbc);
		
		// see if can switch this to radio buttons or a switch.
		// something more obvious for the user
	
		US = new JRadioButton("US", true);
		US.setBackground(Color.white);
		Metric = new JRadioButton("Metric");
		Metric.setBackground(Color.white);
		
		ButtonGroup group = new ButtonGroup();
		group.add(US);
		group.add(Metric);
		
		
		usMetric listener = new usMetric();
		US.addActionListener(listener);
		Metric.addActionListener(listener);
		
		
		gbc.gridx = 4;
		gbc.gridy = 0;
	
		panelSouth.add(US);
		panelSouth.add(Metric);

		GetWeatherListener listener1 = new GetWeatherListener();
		getWeather.addMouseListener(listener1);

		// create new jframe and put the entire contents panel inside.
		// this is done to prevent GUI resizing
		frame = new JFrame("SimpleWeather");
		frame.add(contents);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// LISTENER FOR RADIO BUTTON US/Metric
	private class usMetric implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			boolean former = false;
			Object source = event.getSource();
			
			if(source == US)
			{
				former = isMetric;
				former = false;
			}
			
			if(source == Metric)
			{
				isMetric = !former;
				former = true;
			}
			
		}
		
	}

	// needed in order to set fonts
	private void setFonts()
	{
		UIManager.put("Label.font", fontLabels);
	}
	
	private void placeLabel(JPanel pane, GridBagConstraints gbc, String id, int y) {
		JLabel lb = new JLabel(id + ": ");
		gbc.gridx = 0;
		gbc.gridy = y;
		pane.add(lb, gbc);
	}

	// listener class for the getWeather button
	private class GetWeatherListener implements MouseListener
	{
		public void mouseClicked(MouseEvent event)
		{
			// first, check for network connection
			if (isNetworkUp())
			{
				try
				{
					String zip = validateZIP();
					Calendar tstamp = Calendar.getInstance(); // gets now in the current locale
					String now = GetWeather.buildTimestamp(tstamp.get(Calendar.YEAR),
													tstamp.get(Calendar.MONTH)+1, // 0-indexed months
													tstamp.get(Calendar.DAY_OF_MONTH),
													tstamp.get(Calendar.HOUR_OF_DAY));
					
					tstamp.add(Calendar.DAY_OF_YEAR, 1);
					String tm = GetWeather.buildTimestamp(tstamp.get(Calendar.YEAR),
													tstamp.get(Calendar.MONTH)+1,
													tstamp.get(Calendar.DAY_OF_MONTH),
													tstamp.get(Calendar.HOUR_OF_DAY));

					String units = isMetric? "m" : "e";
					
					GetWeather w = new GetWeather(zip, now, tm, units);
					refreshLabels(w);
				}
				catch (InputMismatchException e)
				{
					errorDialog("Enter a 5 numeric digit US ZIP code");
				}
				
			}
			else
			{
				errorDialog("Check your network connection");
			}
		}
		
		public void mouseEntered(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
		public void mouseReleased(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {}
	}
	
	private boolean isNetworkUp()
	{
		// google's faq should always be up, and it's 1/10 the size of google.com
		// also, it doesn't redirect
		try
		{
			URL url = new URL("https://www.google.com/intl/en/policies/faq/");
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			if (c.getResponseCode() == 200)
				return true;
			else
				return false; // something needs to be done!! by the user, of course
			
		}
		catch (MalformedURLException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
	}
	
	// validates user input, and either returns it or throws an InputMismatchException
	private String validateZIP()
	{
		// allow 5 or 9 digit ZIP codes, but truncate 9 digit ones
		String zip = enterZip.getText();
		if (!(zip.length()==5) && !(zip.length()==9) && !(zip.length()==10))
		{
			// 10 for zip codes like: 21229-3113
			throw new InputMismatchException("ZIP code wrong length");
		} else
		{
			try
			{
				Integer.parseInt(zip.substring(0, 5));
				zip = zip.substring(0, 5);
			} catch (NumberFormatException e)
			{
				// if this is triggered, then the first 5 digits of ZIP aren't numeric
				throw new InputMismatchException("ZIP code not numeric");
			}
		}
		return zip;
	}
	
	// TODO: refreshes the labels to reflect the weather
	private void refreshLabels(GetWeather w)
	{
		// refresh the date
		Calendar today = Calendar.getInstance();
		String datetext = today.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
		datetext += ", " + today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
		datetext += " " + today.get(Calendar.DAY_OF_MONTH);
		date.setText(datetext);
		
		Weather weather = w.getParsed().get(0);
		String unit = isMetric? "C" : "F";
		
		// refresh the weather labels
		temp.setText("" + weather.getCurrent() + unit);
		highTemp.setText("" + weather.getHigh() + unit);
		lowTemp.setText("" + weather.getLow() + unit);
		humid.setText("" + weather.getHumidity() + "%");
		howMuchRain.setText("" + weather.getPrecipitation() + "%");
		cloud.setText(weather.cloudiness());
		wind.setText(weather.windiness());
		
	}
	
	// TODO: pops up an error dialog with the specified message
	private void errorDialog(String msg)
	{
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args)
	{
		SimpleWeather gui = new SimpleWeather();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
