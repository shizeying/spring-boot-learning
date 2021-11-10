package com.example.utils;

import com.google.common.collect.Lists;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.GroupByElement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class JsqlparserTest {
	
	@SneakyThrows
	@Test
	public void sqlSelectItems() {
		System.out.println(test_select_items("select a,b,c,d as f from a"));
		
		
	}
	
	
	/**
	 * 查询字段
	 *
	 * @param sql sql
	 * @return {@link List}<{@link String}>
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static List<String> test_select_items(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) CCJSqlParserUtil.parse(new StringReader(sql));
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		return Lists.newArrayList(plain.getSelectItems())
				.stream()
				.filter(Objects::nonNull)
				.map(SelectItem::toString)
				.map(item -> StringUtils.containsIgnoreCase(item, "AS") ?
						StringUtils.split(item,"AS")[1]:
						StringUtils.containsWhitespace(item) ? item.split("\\s")[1] : item)
				.map(item->item.replaceAll("\\s",""))
				.collect(Collectors.toList());
	}
	
	/**
	 * ▪️查询字段
	 *
	 * @param sql sql
	 * @return {@link List}<{@link String}>
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static List<String> test_select_join(String sql) throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse(sql);
		Select selectStatement = (Select) statement;
		PlainSelect plain = (PlainSelect) selectStatement.getSelectBody();
		List<Join> joinList = plain.getJoins();
		List<String> tablewithjoin = new ArrayList<String>();
		if (joinList != null) {
			for (Join join : joinList) {
				join.setLeft(false);
				tablewithjoin.add(join.toString());
				//注意 ， leftjoin rightjoin 等等的to string()区别
			}
		}
		return tablewithjoin;
	}
	
	/**
	 * ▪️查询表名 table
	 *
	 * @param sql sql
	 * @return {@link List}<{@link String}>
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static List<String> test_select_table(String sql) throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse(sql);
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		return tablesNamesFinder.getTableList(selectStatement);
	}
	
	/**
	 * ▪️查询 where
	 *
	 * @param sql sql
	 * @return {@link String}
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static String test_select_where(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) parserManager.parse(new StringReader(sql));
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		Expression where_expression = plain.getWhere();
		return where_expression.toString();
	}
	
	/**
	 * ▪️查询 group by
	 *
	 * @param sql sql
	 * @return {@link List}<{@link String}>
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static List<String> test_select_groupby(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) parserManager.parse(new StringReader(sql));
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		final GroupByElement groupBy = plain.getGroupBy();
		//List<Expression> GroupByColumnReferences = plain.getGroupBy();
		List<String> str_groupby = new ArrayList<String>();
		//if (GroupByColumnReferences != null) {
		//	for (Expression groupByColumnReference : GroupByColumnReferences) {
		//		str_groupby.add(groupByColumnReference.toString());
		//	}
		//}
		return str_groupby;
	}
	
	/**
	 * ▪️查询 order by
	 *
	 * @param sql sql
	 * @return {@link List}<{@link String}>
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static List<String> test_select_orderby(String sql) throws JSQLParserException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) parserManager.parse(new StringReader(sql));
		PlainSelect plain = (PlainSelect) select.getSelectBody();
		List<OrderByElement> OrderByElements = plain.getOrderByElements();
		List<String> str_orderby = new ArrayList<String>();
		if (OrderByElements != null) {
			for (OrderByElement orderByElement : OrderByElements) {
				str_orderby.add(orderByElement.toString());
			}
		}
		return str_orderby;
	}
	
	/**
	 * ▪️查询 子查询
	 *
	 * @param selectBody 查询身体
	 * @return {@link Map}
	 * @throws JSQLParserException jsqlparser例外
	 */
	private static Map test_select_subselect(SelectBody selectBody) throws JSQLParserException {
		Map<String, String> map = new HashMap<String, String>();
		
		if (selectBody instanceof PlainSelect) {
			List<SelectItem> selectItems = ((PlainSelect) selectBody).getSelectItems();
			for (SelectItem selectItem : selectItems) {
				if (selectItem.toString().contains("(") && selectItem.toString().contains(")")) {
					map.put("selectItemsSubselect", selectItem.toString());
				}
			}
			
			Expression where = ((PlainSelect) selectBody).getWhere();
			String whereStr = where.toString();
			if (whereStr.contains("(") && whereStr.contains(")")) {
				int firstIndex = whereStr.indexOf("(");
				int lastIndex = whereStr.lastIndexOf(")");
				CharSequence charSequence = whereStr.subSequence(firstIndex, lastIndex + 1);
				map.put("whereSubselect", charSequence.toString());
			}
			
			FromItem fromItem = ((PlainSelect) selectBody).getFromItem();
			if (fromItem instanceof SubSelect) {
				map.put("fromItemSubselect", fromItem.toString());
			}
			
		} else if (selectBody instanceof WithItem) {
			test_select_subselect(((WithItem) selectBody).getSubSelect().getSelectBody());
		}
		return map;
	}
}
