import java.awt.*;
import javax.swing.*;

public class LFGraph{
    public static void main (String[] args){

        // フレームの大きさを設定
		int w = 500;
		int h = 500;

		// フレームを作成
		JFrame frame = new JFrame();

		// // タイトル名を設定
		frame.setTitle( "LF Moving" );


		// 内側フレームの大きさを設定
		frame.getContentPane().setPreferredSize( new Dimension( w, h ) );
		frame.pack();

		// ”×”ボタンを押した時の処理を設定
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		// フレームにパネルを追加
		MyPanel panel = new MyPanel();
		frame.getContentPane().add( panel );

		// フレームを表示
		frame.setVisible( true );
	}
}

// JPanelを継承したMyPanelを作成
class MyPanel extends JPanel {
	public MyPanel() {
		// 背景色を黒(black)に設定
		setBackground( Color.white );
	}

	// 関数(LFStep長計算)
	private double f()
	{
		LF LFstep_length = new LF();
        double LFSTEP = LFstep_length.LFstep();
        //System.out.println(LFSTEP);
        return LFSTEP;
	}


	// ラインの開始座標
	private int line_x1 = 0;
	private int line_y1 = 0;

	// ラインの開始座標の設定
	private void moveTo(Graphics g, int x, int y )
	{
		line_x1 = x;
		line_y1 = y;
	}

	// ラインの表示
	private void lineTo(Graphics g, int x, int y )
	{
		g.drawLine( line_x1, line_y1, x, y );
		line_x1 = x;
		line_y1 = y;
	}

    double x_sum;
    double y_sum;

	// 描画
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );

		// フレームの大きさを取得
		int frame_w = getWidth();
		int frame_h = getHeight();

		// 座標変換クラスの作成
		Transformation2d trans = new Transformation2d();

		// 座標を設定
		// (,)-(,)の範囲がフレーム全体に表示する
        double range_x = 200;
        double range_y = 200;
		trans.set( -range_x, range_y, range_x, -range_y, 0.0, 0.0,
			(double)( frame_w - 1 ), (double)( frame_h - 1 ) );

		// 原点の画像座標を取得
		double origin_x = trans.getX( 0.0 );
		double origin_y = trans.getY( 0.0 );

		// 座標軸の色を黒
		g.setColor( Color.black );

		// x座標の描画
		g.drawLine( 0, (int)origin_y, frame_w - 1, (int)origin_y );

		// y座標の描画
		g.drawLine( (int)origin_x, 0, (int)origin_x, frame_h - 1 );

		// LFの移動距離の描画
		// ランダムウォークの色を赤
		g.setColor( Color.red );

		// LFの移動を表示
		boolean isStart = true;
		for ( int i = 0; i <= 1000; i++ ) {
			// 計算結果をx,yに代入
            double x = f();
			double y = f();

            x_sum = x_sum + x;
            y_sum = y_sum + y;

			// 数学座標( deg, value )を
			// 画像座標( line_x2, line_x2 )に変換
			int px = (int)trans.getX( x_sum );
			int py = (int)trans.getY( y_sum );
			
			//
			if ( true == isStart ) {
				moveTo( g, px, py );
				isStart = false;
			}
			else
				lineTo( g, px, py );
		}
	}
}