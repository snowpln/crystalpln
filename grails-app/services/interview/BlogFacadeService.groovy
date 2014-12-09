package interview

import com.lerss.ent.api.BlogFacade
import com.lerss.ent.api.BlogEntryDTO
import grails.transaction.Transactional

@Transactional
class BlogFacadeService implements BlogFacade {

	@Override
	void publish(BlogEntryDTO entry) { //捕获异常
		try {
			new BlogEntry(title: entry.title, content: entry.content, dateCreated: entry.dateCreated).save()
		} catch (Exception e) {
			println new String("保存失败".getBytes(),"utf-8")
			throw new RuntimeException(new String("保存失败".getBytes(),"utf-8"))
        	}
	}

	@Override
	List<BlogEntryDTO> getRecentEntries(int n) {
		def blogEntryDTOList = new ArrayList<BlogEntryDTO>()
		def blogList = BlogEntry.listOrderBydateCreated(max: n, offset: 0, order: "desc")
		blogList.each { blog ->
		blogEntryDTOList.add(toDTO(blog))
		}
		return blogEntryDTOList
	}

	BlogEntryDTO toDTO(BlogEntry entry) {
		def blogEntryDTO = new BlogEntryDTO(title: entry.title, content: entry.content, dateCreated: entry.dateCreated)
		return blogEntryDTO
	}
}