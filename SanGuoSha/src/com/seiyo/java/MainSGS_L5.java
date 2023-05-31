package com.seiyo.java;

import java.util.Scanner;

public class MainSGS_L5 {

	static Hero[] wjArray = new Hero[6];
	static Card[] cards = new Card[104];

	static {
		//a武将初始化
		wjArray[0] = new Hero("刘备",4,"仁德");
		wjArray[1] = new Hero("赵云",4,"龙胆");
		wjArray[2] = new Hero("曹操",4,"奸雄");
		wjArray[3] = new Hero("夏侯淳",4,"刚烈");
		wjArray[4] = new Hero("孙权",4,"制衡");
		wjArray[5] = new Hero("周瑜",3,"反间");
		//b牌库初始化
		for(int i = 0; i < cards.length; i++) {
			if(i <= 20) {//0-20 = 21
				cards[i] = new Card("黑桃","杀");
			}else if(i >= 21 && i < 42) {//21 - 42 = 21
				cards[i] = new Card("梅花","杀");
			}else if(i >= 42 && i < 84) {// 43 - 85 = 42
				cards[i] = new Card("方块","闪");
			}else {// 86 - 106 = 20
				cards[i] = new Card("红桃","桃");
			}
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		//a1设置武将的卡牌数组
		Card[] cardArray1 = new Card[20];
		Card[] cardArray2 = new Card[20];

		//a遍历当前的武将数组进行让用户选择武将
		System.out.println("序号\t武将名\t血量\t技能");
		for(int i = 0; i < wjArray.length; i++) {
			System.out.println((i+1) + "." + wjArray[i].wjName + "\t" + wjArray[i].wjBlood + "\t" + wjArray[i].wjSkill);
		}
		//b让用户选择武将
		System.out.print("请选择需要的武将序号：");
		int num = scanner.nextInt();
		//c设置保存用户选择的武将对象
		Hero w1 = null;
		//d循环遍历判断用户选择的武将
		for(int i = 0; i < wjArray.length; i++) {
			if((i+1) == num) {
				w1 = wjArray[i];
				w1.dqBlood = w1.wjBlood;
				break;
			}
		}
		//e电脑随机一个武将进行作战  random[0,1)*6    1 2 3 4 5 6  5.9 - 6.9 double int 6.9 6
		int dnNum = (int)(Math.random()*6);
		//f获取电脑随机的武将
		Hero w2 = wjArray[dnNum];
		w2.dqBlood = w2.wjBlood;
		System.out.println("电脑使用的是：" + wjArray[dnNum].wjName + "武将");
		//g初始化用户和电脑的手牌
		System.out.println("发牌给玩家：");
		for(int i = 0; i < 4; i++) {
			int sj = (int)(Math.random()*104);//0-103  103.99999 = 103
			System.out.print("[卡牌" + (i+1) + "]:（" + cards[sj].CardStyle + "）" + cards[sj].CardInfo + "\t");
			cardArray1[i] = cards[sj];
		}
		System.out.println("\n发牌给电脑：");
		for(int i = 0; i < 4; i++) {
			int sj = (int)(Math.random()*104);//0-103  103.99999 = 103
			System.out.print("[卡牌" + (i+1) + "]:（" + cards[sj].CardStyle + "）" + cards[sj].CardInfo + "\t");
			cardArray2[i] = cards[sj];
		}
		//p判断用户出的手牌
		while(true) {
			//b玩家摸牌
			System.out.println("\n玩家摸牌");
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < cardArray1.length; j++) {
					if(cardArray1[j] == null) {
						int sj = (int)(Math.random()*104);
						cardArray1[j] = cards[sj];
						break;
					}
				}
			}
			//c展示当前手牌
			for(int  i = 0; i < cardArray1.length; i++) {
				if(cardArray1[i] != null) {
					System.out.print("[卡牌" + (i+1) + "]:（" + cardArray1[i].CardStyle + "）" + cardArray1[i].CardInfo + "\t");
				}
			}
			while(true) {
				//d用户出牌
				System.out.println("\n请选择要出的牌（序号）：");
				int xh = scanner.nextInt();
				System.out.println("用户出牌：");
				System.out.print("[卡牌" + xh + "]:（" + cardArray1[(xh-1)].CardStyle + "）" + cardArray1[(xh-1)].CardInfo + "\t");
				if(cardArray1[(xh-1)].CardInfo.equals("杀")) {
					//a1用户出杀的时候
					//a2电脑响应了一张闪，电脑没有闪 掉血
					int a = 0;
					for(int i = 0; i < cardArray2.length; i++) {
						if(cardArray2[i] != null) {
							if(cardArray2[i].CardInfo.equals("闪")) {
								System.out.println(w2.wjName + "出了一张：闪");
								a = 1;
								cardArray2[i] = null;
								break;
							}
						}
					}
					if(a == 0) {
						System.out.println(w2.wjName + "扣除一滴血！");
						w2.dqBlood = w2.dqBlood - 1;
						if(w2.dqBlood <= 0) {
							System.out.println("恭喜你，赢了！");
							System.exit(0);
						}
					}
					cardArray1[(xh-1)] = null;
				}else if(cardArray1[(xh-1)].CardInfo.equals("桃")){
					//a1用户出桃的时候
					if(w1.dqBlood == w1.wjBlood) {
						System.out.println("当前的武将血量是满的，不能使用桃子！");
						continue;
					}else {
						w1.dqBlood = w1.dqBlood + 1;
						//f清掉用户出的牌
						cardArray1[(xh-1)] = null;
					}
				}else {
					System.out.println("用户过牌！");
				}
				break;
			}
			//b电脑摸牌
			System.out.println("\n电脑摸牌");
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < cardArray2.length; j++) {
					if(cardArray2[j] == null) {
						int sj = (int)(Math.random()*104);
						cardArray2[j] = cards[sj];
						break;
					}
				}
			}
			//c电脑展示当前手牌
			for(int  i = 0; i < cardArray2.length; i++) {
				if(cardArray2[i] != null) {
					System.out.print("[卡牌" + (i+1) + "]:（" + cardArray2[i].CardStyle + "）" +
									cardArray2[i].CardInfo + "\t");
				}
			}
			//d电脑出牌
			int b1 = 0;
			int b2 = 0;
			for(int j = 0; j < cardArray2.length; j++) {
				if(cardArray2[j] != null) {
					if(cardArray2[j].CardInfo.equals("桃")) {
						b1 = 1;
					}
					if(cardArray2[j].CardInfo.equals("杀")) {
						b2 = 1;
					}
				}
			}
			if(w2.dqBlood == w2.wjBlood && b2 == 1) {
				//a电脑没有掉血，但是有手牌杀
				for(int i = 0 ; i < cardArray2.length; i++) {
					if(cardArray2[i] != null) {
						if(cardArray2[i].CardInfo.equals("杀")) {
							System.out.println("\n电脑出牌");
							System.out.print("[卡牌" + (i+1) + "]:（" + cardArray2[i].CardStyle + "）" +
											cardArray2[(i)].CardInfo + "\t");
							cardArray2[i] = null;
							break;
						}
					}
				}
				int a = 0;
				for(int i = 0; i < cardArray1.length; i++) {
					if(cardArray1[i] != null) {
						if(cardArray1[i].CardInfo.equals("闪")) {
							System.out.println(w1.wjName + "出了一张：闪");
							a = 1;
							cardArray1[i] = null;
							break;
						}
					}
				}
				if(a == 0) {
					System.out.println(w1.wjName + "扣除一滴血！");
					w1.dqBlood = w1.dqBlood - 1;
					if(w1.dqBlood <= 0) {
						System.out.println("你输了！");
						System.exit(0);
					}
				}
			}else if(w2.dqBlood != w2.wjBlood && b1 == 1) {
				//b电脑掉血了，但是有桃子
				for(int i = 0 ; i < cardArray2.length; i++) {
					if(cardArray2[i].CardInfo.equals("桃")) {
						System.out.println("电脑出牌");
						System.out.print("[卡牌" + (i+1) + "]:（" + cardArray2[i].CardStyle + "）" + cardArray2[i].CardInfo + "\t");
						cardArray2[i] = null;
						w2.dqBlood = w2.dqBlood + 1;
						break;
					}
				}
			}else if(w2.dqBlood != w2.wjBlood && b2 == 1 && b1 == 0) {
				//c电脑出杀
				for(int i = 0 ; i < cardArray2.length; i++) {
					if(cardArray2[i].CardInfo.equals("杀")) {
						System.out.println("电脑出牌");
						System.out.print("[卡牌" + (i+1) + "]:（" + cardArray2[i].CardStyle + "）" + cardArray2[i].CardInfo + "\t");
						cardArray2[i] = null;
						break;
					}
				}
				int a = 0;
				for(int i = 0; i < cardArray1.length; i++) {
					if(cardArray1[i] != null) {
						if(cardArray1[i].CardInfo.equals("闪")) {
							System.out.println(w1.wjName + "出了一张：闪");
							a = 1;
							cardArray1[i] = null;
							break;
						}
					}
				}
				if(a == 0) {
					System.out.println(w1.wjName + "扣除一滴血！");
					w1.dqBlood = w1.dqBlood - 1;
					if(w1.dqBlood < 0) {
						System.out.println("你输了！");
						System.exit(0);
					}
				}
			}else {
				System.out.println("电脑过！");
			}
		}


	}

}
