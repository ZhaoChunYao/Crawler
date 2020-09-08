package zhaochunyao.crawler.service;

import zhaochunyao.crawler.entity.Page;

//This interface stipulates that all page downloader should download and save the web page

public interface DownloadService {
	public Page downloadAndSave(String url);
}
