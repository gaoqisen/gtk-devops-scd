package com.gqs.algorithm.other;

import java.util.Stack;

public class D04LinkList {

    public static void main(String[] args) {
        Hero hero = new Hero(1, "高");
        Hero hero1 = new Hero(2, "李");
        Hero hero2 = new Hero(3, "刘");
        Hero hero3 = new Hero(4, "高吴");

        LinkHeroList list = new LinkHeroList();
        list.addOrder(hero);
        list.addOrder(hero1);
        list.addOrder(hero2);
        list.addOrder(hero3);
        list.list();
        System.out.println("逆序打印");
        list.reversePrint();

        System.out.println("反转");
        list.reverse();
        list.list();

        System.out.println(list.size());

        System.out.println(list.findLastIndexNode(3).toString());

        System.out.println("delete ----- ");
        list.del(3);
        Hero hero4 = new Hero(4, "张");

        list.update(hero4);
        list.list();
        System.out.println(list.size());


    }

}

class LinkHeroList{

    Hero head = new Hero();

    /**
     * 从尾到头打印单链表
     */
    public void reversePrint(){
        if(head.getNext() == null) {
            return;
        }

        Stack<Hero> stack = new Stack<>();
        Hero temp = head.getNext();
        while (temp != null) {
            stack.push(temp);
            temp = temp.getNext();
        }

        while (stack.size() > 0) {
            System.out.println(stack.pop().toString());
        }
    }

    /**
     * 反转
     */
    public void reverse(){
        if(head.getNext() == null) {
            return;
        }
        Hero cur = new Hero();
        Hero next = null;
        Hero reverse = new Hero();

        while (cur != null) {
            next = cur.getNext();
            cur.setNext(reverse.getNext());
            reverse.setNext(cur);
            cur = next;
        }
        head.setNext(reverse.getNext());
    }

    /**
     * 获取最后第index的节点
     */
    public Hero findLastIndexNode(int index) {
        if(head.getNext() == null) {
            return null;
        }
        Hero temp = head.getNext();
        for (int i = 0; i < size() - index; i++) {
            temp = temp.getNext();
        }
        return temp;
    }

    public int size() {
        int i = 0;
        Hero temp = head;
        while (temp.getNext() != null) {
            i++;
            temp = temp.getNext();
        }
        return i;
    }

    /**
     * 有序添加节点
     */
    public void addOrder(Hero hero) {
        Hero temp = head;
        while (true){
            if(temp.getNext() == null) {
                temp.setNext(hero);
                break;
            }
            if(temp.getNext().getNo() > hero.getNo()) {
                hero.setNext(temp.getNext());
                temp.setNext(hero);
                break;
            }
            temp = temp.getNext();
        }
    }

    /**
     * 将数据放在最后一个节点
     */
    public void add(Hero hero) {
        Hero temp = head;
        while (true) {
            if(temp.getNext() == null) {
                temp.setNext(hero);
                break;
            }
            temp = temp.getNext();
        }
    }

    public void del(int no) {
        Hero temp = head;
        while (true) {
            if(temp.getNext() == null) {
                break;
            }
            if(temp.getNext().getNo() == no) {
                temp.setNext(temp.getNext().getNext());
                break;
            }
            temp = temp.getNext();
        }
    }

    public void update(Hero hero) {
        Hero temp = head;
        while (true) {
            if(temp.getNo() == hero.getNo()) {
                temp.setName(hero.getName());
                break;
            }
            if(temp.getNext() == null) {
                break;
            }

            temp = temp.getNext();
        }
    }

    public void list() {
        Hero temp = head.getNext();
        while (true) {
            System.out.println(temp.toString());
            if(temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
        }
    }

}

class Hero{

    private int no;

    private String name;

    private Hero next;

    public Hero() {

    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hero getNext() {
        return next;
    }

    public void setNext(Hero next) {
        this.next = next;
    }

    public Hero(int no, String name) {
        this.no = no;
        this.name = name;
    }


    @Override
    public String toString() {
        return "Hero{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
