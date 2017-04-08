package de.fzi.cep.sepa.flink.samples.count.aggregate;

import de.fzi.cep.sepa.model.impl.graph.SepaInvocation;
import de.fzi.cep.sepa.runtime.param.BindingParameters;

import java.util.List;

public class CountAggregateParameters extends BindingParameters {

	private int timeWindow;
	private List<String> groupBy;
	private TimeScale timeScale;
	private List<String> selectProperties;

	public CountAggregateParameters(SepaInvocation graph, int timeWindow, List<String> groupBy, TimeScale timeScale, List<String> selectProperties) {
		super(graph);
		this.timeScale = timeScale;
		this.groupBy = groupBy;
		this.timeWindow = timeWindow;
		this.selectProperties = selectProperties;
	}

	public int getTimeWindow() {
		return timeWindow;
	}

	public void setTimeWindow(int timeWindow) {
		this.timeWindow = timeWindow;
	}

	public List<String> getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(List<String> groupBy) {
		this.groupBy = groupBy;
	}

	public TimeScale getTimeScale() {
		return timeScale;
	}

	public void setTimeScale(TimeScale timeScale) {
		this.timeScale = timeScale;
	}

	public List<String> getSelectProperties() {
		return selectProperties;
	}

	public void setSelectProperties(List<String> selectProperties) {
		this.selectProperties = selectProperties;
	}


}