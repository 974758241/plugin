package com.packtpub.e4.clock.ui.internal;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TimeZoneComparator implements Comparator<ZoneId> {
	public int compare(ZoneId o1, ZoneId o2) {
		return o1.getId().compareTo(o2.getId());
	}

	public static Map<String, Set<ZoneId>> getTimeZones() {
		Supplier<Set<ZoneId>> sortedZones = () -> new TreeSet<>(new TimeZoneComparator());
		return ZoneId.getAvailableZoneIds().stream() // stream
				.filter(s -> s.contains("/")) // with / in them
				.map(ZoneId::of) // convert String to ZoneId
				.collect(Collectors.groupingBy( // and group by
						z -> z.getId().split("/")[0], TreeMap::new, Collectors.toCollection(sortedZones)));
	}
}