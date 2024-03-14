import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LinkedList
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Alec
 * @Create 2024/3/14 14:03
 * @Version 1.0
 */
public class LinkedList {

    static class MyLinkedList {

        int val;
        MyLinkedList next = null;

        public MyLinkedList myLinkedList() {
            return new MyLinkedList();
        }

        public int get(int index) {
            int i = 0;
            MyLinkedList res = this;
            while (i <= index && res != null) {
                res = res.next;
                i++;
            }
            return res != null ? res.val : -1;
        }

        public void addAtHead(int val) {
            MyLinkedList newNode = new MyLinkedList();
            newNode.val = val;
            newNode.next = this.next;
            this.next = newNode;
        }

        public void addAtTail(int val) {
            MyLinkedList node = this;
            while (node.next != null) {
                node = node.next;
            }
            node.next = new MyLinkedList();
            node.next.val = val;
        }

        public void addAtIndex(int index, int val) {
            MyLinkedList node = this, newNode = new MyLinkedList();
            for (int i = 0; i < index && node != null; i++) {
                node = node.next;
            }
            if (node == null) return;
            newNode.val = val;
            newNode.next = node.next;
            node.next = newNode;
        }

        public void deleteAtIndex(int index) {
            MyLinkedList node = this;
            for (int i = 0; i < index && node != null; i++) {
                node = node.next;
            }
            if (node == null || node.next == null) return;
            node.next = node.next.next;
        }
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    static class Solution {

        // 环形链表
        public boolean hasCycle(ListNode head) {
            boolean jud = false;
            if(head == null || head.next == null) return false;
            ListNode slow = head, fast = head;
            while(fast != null){
                if(fast.next != null && fast.next.next != null){
                    fast = fast.next.next;
                }else{
                    return false;
                }
                slow = slow.next;
                if(slow == fast) return true;
            }
            return true;
        }

        // 环形链表 II
        // 关键参数：到入环点的距离D，入环点到首次相遇位置的距离S1，首次相遇点到入环点的距离S2,其中S1+S2=环的大小
        public ListNode detectCycle(ListNode head) {
            boolean jud = false;
            ListNode meet;
            if(head == null || head.next == null) return null;
            ListNode slow = head, fast = head;
            while(fast != null){
                if(fast.next != null && fast.next.next != null){
                    fast = fast.next.next;
                }else{
                    return null;
                }
                slow = slow.next;
                if(slow == fast) {
                    meet = fast;
                    break;
                }
            }
            slow = head;
            while(slow != fast){
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }

        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            int lenA=0, lenB=0;
            ListNode pa = headA,pb = headB;
            while(pa != null){
                pa = pa.next; lenA ++;
            }
            while(pb != null){
                pb = pb.next; lenB ++;
            }
            pa = headA;
            pb = headB;
            while(lenA > lenB){
                pa = pa.next;
                lenA--;
            }
            while(lenB > lenA){
                pb = pb.next;
                lenB--;
            }
            while(pa != null && pa != pb){
                pa = pa.next;
                pb = pb.next;
            }
            if(pa == null) return null;
            else{
                return pa;
            }
        }

        // 删除链表的倒数第N个节点
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode right = head,left = head;
            if(n == 1 && head.next == null) return null;
            for (int i = 0; i < n; i++) {
                if(right == null) return null;
                right = right.next;
            }
            if(right == null){
                return head.next;
            }
            right = right.next;
            while(right != null){
                left = left.next;
                right = right.next;
            }
            left.next = left.next.next;
            return head;
        }

        // 反转链表(递归方法)
        public ListNode reverseList(ListNode head) {
            if(head == null) return null;
            if(head.next == null) return head;
            ListNode h = reverseList(head.next), p = h;
            while(p != null && p.next != null){
                p = p.next;
            }
            p.next = head;
            head.next = null;
            return h;
        }

        public ListNode reverseList_optimized(ListNode head) {
            if(head == null || head.next == null) return head;
            ListNode next = head.next;
            ListNode h = reverseList(head.next);
            next.next = head;
            head.next = null;
            return h;
        }

        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode head = new ListNode(), tail = head;
            ListNode p1=list1, p2 = list2;
            while(list1 != null && list2 != null){
                if(list1.val < list2.val){
                    p1 = list1.next;
                    tail.next = list1;
                    tail = list1;
                    list1 = p1;
                }else{
                    p2 = list2.next;
                    tail.next = list2;
                    tail = list2;
                    list2 = p2;
                }
            }
            while(list1 != null){
                p1 = list1.next;
                tail.next = list1;
                tail = list1;
                list1 = p1;
            }
            while(list2 != null){
                p2 = list2.next;
                tail.next = list2;
                tail = list2;
                list2 = p2;
            }
            return head.next;
        }


        // 两数相加
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 创建一个头结点会方便很多，返回的时候返回 res.next 即可自动去掉这个头
            ListNode res = new ListNode(), rear = res;
            // flag 表示当前是否需要进位
            boolean flag = false;
            while(l1 != null || l2 != null){
                // 注意判空，如果为空则取0值进行计算
                int val1 = l1==null?0:l1.val;
                int val2 = l2==null?0:l2.val;
                ListNode curr = new ListNode();
                rear.next = curr;
                // 判断上一次是否进位，并判断这一次是否要进位
                if(flag) {
                    curr.val = (val1 + val2 + 1) % 10;
                    flag = (val1 + val2 + 1) >= 10;
                }else{
                    curr.val = (val1 + val2) % 10;
                    flag = (val1 + val2) >= 10;
                }
                l1 = l1==null?l1:l1.next;
                l2 = l2==null?l2:l2.next;
                rear = curr;
            }
            // 处理最后一次进位可能存在的遗留问题
            if(flag){
                rear.next = new ListNode(1);
            }
            return res.next;

        }

/*        class Node {
            public int val;
            public Node prev;
            public Node next;
            public Node child;
        };*/

        // 扁平化多级双向链表
        public Node flatten(Node head) {
            // 递归退出条件
            if(head == null) return null;
            if(head.child == null) {
                head.next = flatten(head.next);
                return head;
            }
            // 对child处理
            Node headNext = head.next;
            head.next = flatten(head.child);
            head.next.prev = head.next!=null?head:null;
            // 对next处理
            Node lastNode = head;
            while(lastNode.next != null) lastNode = lastNode.next;
            lastNode.next = flatten(headNext);
            if(lastNode.next != null)
                lastNode.next.prev =lastNode;
            head.child = null;

            return head;
        }

        class Node {
            int val;
            Node next;
            Node random;

            public Node(int val) {
                this.val = val;
                this.next = null;
                this.random = null;
            }
        }

        // 复制带随机指针的链表
        public Node copyRandomList(Node head) {
            List<Node> origin = new ArrayList<>();
            List<Node> result = new ArrayList<>();
            if(head == null) return null;
            while(head != null){
                Node curr = new Node(head.val);
                // 把前一个连到新来的这个节点
                if(!result.isEmpty()){
                    result.get(result.size()-1).next = curr;
                }
                origin.add(head);
                result.add(curr);
                head = head.next;
            }
            // 处理最后一个节点指向null
            result.get(result.size()-1).next = null;
            // 从头处理 random 的指向
            head = origin.get(0);
            Node p = result.get(0);
            while(head != null){
                if(head.random!=null)
                    p.random = result.get(origin.indexOf(head.random));
                head = head.next;
                p = p.next;
            }
            return result.get(0);
        }

        public Node copyRandomList_optimized(Node head) {
            if(head == null) return null;
            Map<Node,Node> map = new HashMap<>();
            Node origin, newHead, result;
            origin = head;
            result = newHead = new Node(head.val);
            map.put(origin, result);
            origin = origin.next;
            while(origin != null){
                result.next = new Node(origin.val);
                result = result.next;
                map.put(origin, result);
                origin = origin.next;
            }
            origin = head;
            result = newHead;
            while(origin != null){
                if(origin.random != null){
                    result.random = map.get(origin.random);
                }
                origin = origin.next;
                result = result.next;
            }
            return newHead;
        }

        // 旋转链表
        public ListNode rotateRight(ListNode head, int k) {
            if(head == null || head.next == null){
                return head;
            }
            int n = 1;
            ListNode curr = head;
            while(curr.next != null){
                n++;
                curr = curr.next;
            }
            // 链表成环
            curr.next = head;
            // 旋转k%n次，共k%n个后面的元素到前面，后面还有n-k%n个元素，所以head移动n-k%n次找到头结点，也就是curr移动n-k%n次找到尾节点
            for (int i = 0; i < n-k%n; i++) {
                curr = curr.next;
            }
            head = curr.next;
            curr.next = null;
            return head;
            
        }


    }

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        int val = myLinkedList.get(1);              // 返回 2
        System.out.println(val);
        myLinkedList.addAtTail(3);
        myLinkedList.addAtIndex(1, 2);    // 链表变为 1->2->3
        myLinkedList.deleteAtIndex(1);    // 现在，链表变为 1->3
        myLinkedList.get(1);              // 返回 3
    }

}
