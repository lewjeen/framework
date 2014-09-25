package com.mocha.unitcode.mina.client;

import org.junit.Test;

import com.mocha.tydb_common.entity.TodoEntity;

public class TestCoreClient {
	@Test
	public void testClient() {
		try {
			CoreClient client = CoreClientFactory.getInstance("127.0.0.1",
					7890, "test@hq.cmcc");
			TodoEntity todo = new TodoEntity();
			todo.setAppItemId("ascvv");
			todo.setItemUrl("http://www.baidu.com/s?wd=http%3&tn=a");
			String title = "LLLLLLLLLLLLLLLLLL“改革开放胆子要大一些，敢于试验，不能像小脚女人一样。看准了的，就大胆地试，大胆地闯。”“走不出一条新路，就干不出新的事业。”“现在问题相当多，要解决，没有一股劲不行。要敢字当头，横下一条心。”“改革开放中许许多多的东西，都是群众在实践中提出来的”，“绝不是一个人脑筋就可以钻出什么新东西来”，“这是群众的智慧，集体的智慧”。昔日邓小平关于改革的形象描述再次被提及，今天听来依然振聋发聩。虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。LLLLLLLLlllllllasdlllllllllljjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjllllllllllllllllllllllll";
			todo.setItemTitle(title);
			for (int i = 0; i < 10; i++) {
				client.sendTodoEntity(todo);
			}

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
