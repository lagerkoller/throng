package de.johannesluderschmidt.throng.proxy;

import com.illposed.osc.OSCPortOut;

public interface IThrongProxyTerminator {
	void sendEmptyBundlesWithSource(SourceIpPortBean bean);
	void sendEmptyBundlesWithoutSource(OSCPortOut oscPortOut);
}
