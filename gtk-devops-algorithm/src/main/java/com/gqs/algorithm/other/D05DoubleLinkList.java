package com.gqs.algorithm.other;


public class D05DoubleLinkList {

    public static void main(String[] args) {
        Hero2 hero = new Hero2(1, "高");
        Hero2 hero1 = new Hero2(2, "李");
        Hero2 hero2 = new Hero2(3, "刘");
        Hero2 hero3 = new Hero2(4, "高吴");

        LinkHeroList2 list = new LinkHeroList2();
        list.add(hero);
        list.add(hero1);
        list.add(hero2);
        list.add(hero3);
        list.list();

        System.out.println("delete ----- ");
        list.del(3);
        Hero2 hero4 = new Hero2(4, "张");

        list.update(hero4);
        list.list();
    }

}

class LinkHeroList2{

    Hero2 head = new Hero2();

    public void add(Hero2 hero) {
        Hero2 temp = head;
        while (true) {
            if(temp.getNext() == null) {
                temp.setNext(hero);
                break;
            }
            temp = temp.getNext();
        }
        temp.setNext(hero);
        hero.setPre(temp);
    }

    public void update(Hero2 hero) {
        Hero2 temp = head;
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

    /**
     * 可以直接找到节点
     */
    public void del(int no) {
        if(head.getNext() == null) {
            return;
        }
        Hero2 temp = head.getNext();
        boolean flag = false;
        while (true) {
            if(temp.getNext() == null) {
                break;
            }
            if(temp.getNo() == no) {
                flag = true;
                break;
            }
            temp = temp.getNext();
        }

        if(flag) {
            // 将前一个节点指向下一个节点
            temp.getPre().setNext(temp.getNext());
            if(temp.getNext() != null) {
                // 将下一个的前一个节点指向临时节点的前一个节点
                temp.getNext().setPre(temp.getPre());
            }
        }
    }

    public Hero2 getHead() {
        return head;
    }

    public void list() {
        Hero2 temp = head.getNext();
        while (true) {
            System.out.println(temp.toString());
            if(temp.getNext() == null) {
                break;
            }
            temp = temp.getNext();
        }
    }

}

class Hero2{

    private int no;

    private String name;

    private Hero2 next;
    private Hero2 pre;

    public Hero2() {

    }

    public Hero2 getPre() {
        return pre;
    }

    public void setPre(Hero2 pre) {
        this.pre = pre;
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

    public Hero2 getNext() {
        return next;
    }

    public void setNext(Hero2 next) {
        this.next = next;
    }

    public Hero2(int no, String name) {
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
