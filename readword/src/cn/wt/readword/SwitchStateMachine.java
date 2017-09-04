package cn.wt.readword;

public class SwitchStateMachine  {
	private   StringArrayReader reader;
	private   WordEntity word;
	private   String ch;
    private   StringBuffer str;
    public SwitchStateMachine(StringArrayReader reader, WordEntity word) {
		super();
		this.reader = reader;
		this.word = word;
		str=new StringBuffer();
	}

	public WordEntity getWord() {
		return word;
	}

	enum State {
        Init, AfterDW, AfterName,AfterGZNR,AfterGZYQ,AfterGZJHL,AfterKZQK,AfterKZQK2,
        AfterXMCX,End;
    }

    private State state = State.Init;

    public synchronized void process() throws StringArrayReader.EOFException{
        
        switch (state) {
            case Init:
                ch = reader.read();
                word.setTitle(ch);
                state = State.AfterDW;
                break;
            case AfterDW:
                ch = reader.read();
				int indexOf = ch.indexOf("�е���λ");
				if (indexOf!=-1) {
	               word.setDanwei(ch.substring(indexOf+5));
	               state = State.AfterName;
	               break;
	            }else{
	               state = State.AfterDW;
	               break;
	            } 
	                
            case AfterName:
            	 ch = reader.read();
 				int indexOf2 = ch.indexOf("��Ҫ�����");
 				if (indexOf2!=-1) {
 	               word.setNames(ch.substring(indexOf2+6));
 	               state = State.AfterGZNR;
 	              break;
 	            }else{
 	               state = State.AfterName;
 	              break;
 	            } 
            case AfterGZNR:
           	 	ch = reader.read();
				int indexOf3 = ch.indexOf("��������");
				if (indexOf3!=-1) {
	               word.setGznr(reader.read());//����ȡ���ǹ������ݵ���һ�仰
	               state = State.AfterGZYQ;
	               break;
	            }else{
	               state = State.AfterGZNR;
	               break;
	            }
            case AfterGZYQ:
           	 	ch = reader.read();
				int indexOf4 = ch.indexOf("����Ҫ��");
				if (indexOf4!=-1) {
	               word.setGzyq(reader.read());//����ȡ���ǹ���Ҫ�����һ�仰
	               state = State.AfterGZJHL;
	               break;
	            }else{
	               state = State.AfterGZYQ;
	               break;
	            }
            case AfterGZJHL:
           	 	ch = reader.read();
				int indexOf5 = ch.indexOf("�ƻ�������");
				if (indexOf5!=-1) {
	               word.setJhgzl(reader.read());//����ȡ���Ǽƻ�����������һ�仰
	               state = State.AfterKZQK;
	               break;
	            }else{
	               state = State.AfterGZJHL;
	               break;
	            }
            case AfterKZQK:
            	//ƴ�ӹ�����չ�������
           	 	ch = reader.read();
				int indexOf6 = ch.indexOf("������չ���");
				if (indexOf6!=-1) {
					str.append(reader.read());
	                state = State.AfterKZQK2;
	                break;
	            }else{
	               state = State.AfterKZQK;
	               break;
	            }
            case AfterKZQK2:
           	 	ch = reader.read();
				int indexOf7 = ch.indexOf("��Ŀ��Ч");
				if (indexOf7==-1) {
					str.append(ch);
	                state = State.AfterKZQK2;
	                break;
	            }else{
	            	word.setGzkzqk(str.toString());
	                state = State.AfterXMCX;
	                break;
	            }	
            case AfterXMCX:
            	ch = reader.read();
	            word.setXmcx(ch);//����ȡ������Ŀ��Ч����һ�仰
	            state = State.End;
	            break;
            case End:
            	ch = reader.read();
				int indexOf8 = ch.indexOf("��������");
				if (indexOf8!=-1)
					word.setGzjy(reader.read());
	            break;    
        }
    }

   
}